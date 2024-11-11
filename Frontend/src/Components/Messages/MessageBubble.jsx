const MessageBubble = ({ message, isFromCurrentUser }) => {
  const messageTime = new Date(message.timestamp);

  return (
    <div
      className={`p-3 rounded-lg max-w-[70%] mb-2 shadow-md ${
        isFromCurrentUser
          ? "bg-blue-500 text-white bg-messageblue self-end"
          : "bg-messagegrey text-black self-start"
      }`}
    >
      <p className="text-sm">{message.content}</p>
      <p className="text-xs text-gray-300 mt-1 self-end">
        {messageTime.toLocaleTimeString([], {
          hour: "2-digit",
          minute: "2-digit",
        })}
      </p>
    </div>
  );
};

export default MessageBubble;
