import { useEffect, useState, useCallback } from "react";
import { io } from "socket.io-client";

const env = import.meta.env.VITE_NODE_ENV;

const SOCKET_SERVER_URL =
  env == "dev" ? "http://localhost:8085" : "https://blueroomies.com";

const useSocket = (token, username, isAdmin = false) => {
  const [messages, setMessages] = useState([]);
  const [socket, setSocket] = useState(null);
  const [connected, setConnected] = useState(false);
  const [error, setError] = useState(null);
  const [adminLogs, setAdminLogs] = useState([]);
  const [activeUsers, setActiveUsers] = useState([]);

  useEffect(() => {
    if (!token || !username) return;

    const newSocket = io(SOCKET_SERVER_URL, {
      path: env == "dev" ? undefined : "/chatserver/",
      auth: { token },
      reconnection: true,
      reconnectionAttempts: 5,
      reconnectionDelay: 1000,
      timeout: 20000,
      withCredentials: true,
      transports: ["polling"],
    });

    // Connection handlers
    newSocket.on("connect", () => {
      console.log("Connected to socket server");
      setConnected(true);
      setError(null);
    });

    newSocket.on("connect_error", (err) => {
      console.error("Connection error:", err);
      setError("Failed to connect to chat server");
      setConnected(false);
    });

    // Message handlers
    newSocket.on("private_message", (message) => {
      console.log("message received: " + message);
      const enhancedMessage = createFrontendMessage(message);
      setMessages((prev) => [...prev, enhancedMessage]);
    });

    // Admin event handlers
    if (isAdmin) {
      newSocket.on("admin_log", (log) => {
        setAdminLogs((prev) => [
          ...prev,
          {
            ...log,
            timestamp: new Date(log.timestamp),
          },
        ]);

        // Update active users if connection/disconnection event
        if (log.type === "connection" || log.type === "disconnection") {
          setActiveUsers(log.activeUsers);
        }
      });
    }

    // Error handlers
    newSocket.on("error", (error) => {
      console.error("Socket error:", error);
      setError(error.message);
    });

    newSocket.on("disconnect", (reason) => {
      console.log("Disconnected:", reason);
      setConnected(false);
    });

    setSocket(newSocket);

    return () => {
      if (newSocket.connected) {
        newSocket.disconnect();
      }
    };
  }, [token, username, isAdmin]);

  const sendMessage = useCallback(
    (recipientUsername, content) => {
      if (!socket?.connected) {
        setError("Not connected to chat server");
        return false;
      }

      try {
        socket.emit("private_message", {
          to: recipientUsername,
          content: content.trim(),
        });

        // Don't add to messages here since the server will echo it back
        // through the private_message event if successful
        return true;
      } catch (err) {
        console.error("Failed to send message:", err);
        setError("Failed to send message");
        return false;
      }
    },
    [socket]
  );

  const appendLocalMessageOnSuccess = useCallback((message) => {
    setMessages((prev) => [
      ...prev,
      createFrontendMessage({
        from: username,
        content: message,
        timestamp: new Date(),
      }),
    ]);
  });

  const clearError = useCallback(() => {
    setError(null);
  }, []);

  const clearAdminLogs = useCallback(() => {
    setAdminLogs([]);
  }, []);

  const createFrontendMessage = (message) => {
    return {
      ...message,
      timestamp: new Date(message.timestamp),
    };
  };

  return {
    messages,
    sendMessage,
    connected,
    error,
    clearError,
    appendLocalMessageOnSuccess,
    // Admin features
    adminLogs: isAdmin ? adminLogs : null,
    clearAdminLogs: isAdmin ? clearAdminLogs : null,
    activeUsers: isAdmin ? activeUsers : null,
  };
};

export default useSocket;
