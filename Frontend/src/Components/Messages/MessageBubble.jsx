const MessageBubble = ({ message, isFromCurrentUser }) => {
  return (
    <div
      className={`p-3 rounded-lg max-w-[70%] mb-2 shadow-md ${
        isFromCurrentUser
          ? "bg-blue-500 text-white self-end"
          : "bg-gray-200 text-black self-start"
      }`}
    >
      <p className="text-sm">{message.content}</p>
      <p className="text-xs text-gray-300 mt-1 self-end">
        {new Date(message.timestamp).toLocaleTimeString()}
      </p>
    </div>
  );
};

export default MessageBubble;
