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

      await this.deliverMessage(message);
      await this.saveMessage(message);

      return message;
    } catch (error) {
      throw new MessageError(error.message);
    }
  }

  async validateMessage(to, content) {
    if (!to || !content) {
      throw new MessageError("Invalid message format");
    }

    if (!this.userSessions.get(to)) {
      throw new MessageError("Recipient not found");
    }
  }

  async validatePermission(fromUserId, toUsername) {
    return true;
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

  async saveMessage(message) {
    console.log("Saving message:", message);
  }

  generateId() {
    return Date.now().toString(36) + Math.random().toString(36).substr(2);
  }
}

class MessageError extends Error {
  constructor(message, code = "MESSAGE_ERROR") {
    super(message);
    this.code = code;
  }
}

export default MessageHandler;
