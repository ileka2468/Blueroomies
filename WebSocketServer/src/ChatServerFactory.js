import { createServer as createHttpServer } from "http";
import { createServer as createHttpsServer } from "https";
import { readFileSync } from "fs";
import express from "express";
import ChatServer from "./ChatServer.js";
import ChatServerConfigurationError from "./ChatServerConfigurationError.js";

const key_pem_path = process.env.SSL_KEY_PATH;
const cert_pem_path = process.env.SSL_CERT_PATH;

class ChatServerFactory {
  createChatServer(type) {
    const app = express();

    switch (type) {
      case "http":
        const httpServer = createHttpServer(app);
        return new ChatServer(httpServer, app);

      case "https":
        const httpsServer = createHttpsServer(
          {
            key: readFileSync(key_pem_path),
            cert: readFileSync(cert_pem_path),
          },
          app
        );
        return new ChatServer(httpsServer, app);

      default:
        throw new ChatServerConfigurationError(
          "Invalid server type. Must be 'http' or 'https'."
        );
    }
  }
}

export default ChatServerFactory;
