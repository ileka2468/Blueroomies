import { Server } from "socket.io";
import dotenv from "dotenv";
import axios from "axios";
import MessageHandler from "./MessageHandler.js";

class ChatServer {
  constructor(server, app) {
    dotenv.config();
    console.log(
      "Running chat server in " + process.env.VITE_NODE_ENV + " mode..."
    );
    this.app = app;
    this.server = server;
    this.userSessions = new Map();
    this.adminSessions = new Map();

    this.io = new Server(this.server, {
      path: process.env.VITE_NODE_ENV == "dev" ? undefined : "/chatserver/",
      cors: {
        origin:
          process.env.VITE_NODE_ENV == "dev"
            ? "http://localhost:5173"
            : "https://blueroomies.com",
        methods: ["GET", "POST"],
      },
      connectionStateRecovery: {
        maxDisconnectionDuration: 2 * 60 * 1000,
      },
      pingTimeout: 20000,
      pingInterval: 25000,
      transports: ["polling"],
    });

    this.messageHandler = new MessageHandler(this.io, this.userSessions);

    // Bind methods
    this.handleConnection = this.handleConnection.bind(this);
    this.handlePrivateMessage = this.handlePrivateMessage.bind(this);
    this.handleDisconnection = this.handleDisconnection.bind(this);
    this.handleError = this.handleError.bind(this);
  }

  start() {
    // Setup auth middleware
    this.io.use(this.authenticate.bind(this));

    this.io.on("connection", this.handleConnection);

    // Add health check endpoint
    this.app.get("/health", (req, res) => {
      res.json({
        status: "ok",
        connections: this.userSessions.size,
      });
    });

    const port = process.env.CHAT_SERVER_PORT || 8085;
    this.server.listen(port, () => {
      console.log(`Chat server running on port ${port}`);
    });
  }

  async authenticate(socket, next) {
    try {
      const token = socket.handshake.auth.token;
      if (!token) {
        throw new Error("No token provided");
      }

      console.log("Token received:", token.substring(0, 20) + "...");

      // Verify token with backend endpoint
      try {
        const response = await axios.post(
          "http://localhost:8080/api/messages/security/auth",
          null,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );

        const userData = response.data;
        console.log("Auth response:", userData.userRoles[0].id.userId);

        socket.user = {
          id: userData.id,
          username: userData.username,
          isAdmin: userData.userRoles[0].role.roleName == "ROLE_ADMIN" || false,
        };

        await this.handleExistingSession(userData.username);

        this.userSessions.set(userData.username, socket);
        if (socket.user.isAdmin) {
          this.adminSessions.set(userData.username, socket);
        }

        next();
      } catch (error) {
        console.error(
          "Auth endpoint error:",
          error.response?.data || error.message
        );
        throw new Error("Token validation failed");
      }
    } catch (err) {
      console.error("Authentication error:", err.message);
      next(new Error("Authentication failed"));
    }
  }

  async handleExistingSession(username) {
    const existingSocket = this.userSessions.get(username);
    if (existingSocket) {
      await this.disconnectUser(existingSocket);
    }
  }

  async disconnectUser(socket) {
    socket.disconnect(true);
    this.userSessions.delete(socket.user?.username);
    if (socket.user?.isAdmin) {
      this.adminSessions.delete(socket.user.username);
    }
  }

  handleConnection(socket) {
    const { username, isAdmin } = socket.user;
    console.log(`User connected: ${username}${isAdmin ? " (admin)" : ""}`);

    // Join user's private room
    socket.join(username);

    // Join admin room if admin
    if (isAdmin) {
      socket.join("admin-room");
      this.emitAdminLog("connection", {
        type: "connection",
        username,
        timestamp: new Date(),
        activeUsers: this.userSessions.size,
      });
    }

    // Setup event listeners
    socket.on("private_message", (data) =>
      this.handlePrivateMessage(socket, data)
    );
    socket.on("disconnect", () => this.handleDisconnection(socket));
    socket.on("error", (error) => this.handleError(socket, error));
  }

  async handlePrivateMessage(socket, messageData) {
    try {
      await this.messageHandler.handleMessage(socket, messageData);

      // Log message events to admin channel
      this.emitAdminLog("message", {
        type: "message",
        from: socket.user.username,
        to: messageData.to,
        timestamp: new Date(),
      });
    } catch (error) {
      this.handleError(socket, error);
    }
  }

  handleDisconnection(socket) {
    const { username, isAdmin } = socket.user || {};
    if (username) {
      this.userSessions.delete(username);
      if (isAdmin) {
        this.adminSessions.delete(username);
      }

      this.emitAdminLog("disconnection", {
        type: "disconnection",
        username,
        timestamp: new Date(),
        activeUsers: this.userSessions.size,
      });

      console.log(`User disconnected: ${username}${isAdmin ? " (admin)" : ""}`);
    }
  }

  handleError(socket, error) {
    console.error("Socket error:", error);
    socket.emit("error", {
      message: error.message || "An error occurred",
      code: error.code || "UNKNOWN_ERROR",
      timestamp: new Date(),
    });

    // Log errors to admin channel
    this.emitAdminLog("error", {
      type: "error",
      username: socket.user?.username,
      error: error.message,
      timestamp: new Date(),
    });
  }

  emitAdminLog(event, data) {
    this.io.to("admin-room").emit("admin_log", {
      event,
      ...data,
    });
  }

  getServerStats() {
    return {
      totalUsers: this.userSessions.size,
      totalAdmins: this.adminSessions.size,
      uptime: process.uptime(),
      timestamp: new Date(),
    };
  }

  shutdown() {
    // Log shutdown to admin channel
    this.emitAdminLog("shutdown", {
      type: "shutdown",
      timestamp: new Date(),
      reason: "Server shutdown initiated",
    });

    // Close all connections
    for (const socket of this.userSessions.values()) {
      socket.disconnect(true);
    }

    this.userSessions.clear();
    this.adminSessions.clear();

    this.io.close(() => {
      console.log("Chat server shutting down...");
    });
    this.server.close();
  }
}

export default ChatServer;
