import ChatServerFactory from "./ChatServerFactory.js";

const factory = new ChatServerFactory();
const type = "http";

try {
  const chatServer = factory.createChatServer(type);
  chatServer.start();

  process.on("uncaughtException", (err) => {
    console.error("Uncaught Exception:", err);
    process.exit(1);
  });

  process.on("unhandledRejection", (err) => {
    console.error("Unhandled Rejection:", err);
  });

  process.on("SIGTERM", () => chatServer.shutdown());
  process.on("SIGINT", () => chatServer.shutdown());
} catch (error) {
  console.error("Failed to start chat server:", error);
  process.exit(1);
}
