import axios from "axios";

class MessageHandler {
  constructor(io, userSessions) {
    this.io = io;
    this.userSessions = userSessions;
  }

  async handleMessage(socket, { to, content }) {
    try {
      await this.validateMessage(to, content);
      await this.validatePermission(socket.user.id, to);

      const message = this.createMessage({
        from: socket.user.username,
        content,
        to,
      });

      await this.saveMessage(message, socket.user.jwt);
      await this.deliverMessage(message);

      return message;
    } catch (error) {
      throw new MessageError(error);
    }
  }

  async validateMessage(to, content) {
    console.log(to, content);
    if (!to || !content) {
      throw new MessageError("Invalid message format");
    }

    if (!this.userSessions.get(to)) {
      throw new MessageError("Recipient not found"); // instead of throwing error do nothing as message was silently received by the database.
    }
  }

  async validatePermission(fromUserId, toUsername) {
    return true;
  }

  generateId() {
    const timestamp = Date.now() % 1000000000; // last 9 digits of the timestamp
    const randomPart = Math.floor(Math.random() * 1000000); // Random number
    return (timestamp + randomPart) % 2147483647; // Integer.MAX_VALUE in Java
  }

  createMessage({ from, content, to }) {
    return {
      id: this.generateId(),
      from,
      to,
      content,
      timestamp: new Date(),
    };
  }

  async deliverMessage(message) {
    this.io.to(message.to).emit("private_message", message);
  }

  async saveMessage(message, jwt) {
    console.log("Token at message save: " + jwt);
    console.log("Saving message:", "");
    const payload = {
      id: message.id,
      content: message.content,
      sender: message.from,
      receiver: message.to,
      timestamp: message.timestamp,
    };
    const backendURL =
      process.env.VITE_NODE_ENV === "dev"
        ? "http://localhost:8080/api/messages/create"
        : "http://backend:8080/api/messages/create";

    console.log(jwt);

    const response = await axios.post(backendURL, payload, {
      headers: {
        Authorization: `Bearer ${jwt}`,
      },
    });
    console.log(response.status);
  }
}

class MessageError extends Error {
  constructor(message, code = "MESSAGE_ERROR") {
    super(message);
    this.code = code;
  }
}

export default MessageHandler;
