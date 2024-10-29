class ChatServerConfigurationError extends Error {
  constructor(message, code = "CONFIGURATION_ERROR") {
    this.code = code;
    super(message);
  }
}

export default ChatServerConfigurationError;
