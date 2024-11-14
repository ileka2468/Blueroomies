import MessageBubble from "./MessageBubble";

const MessageList = ({ messages, userData }) => {
  return (
    <div className="p-4 flex flex-col gap-2 h-[400px] overflow-y-auto">
      {messages.map((msg, index) => (
        <div
          key={index}
          className={`flex ${
            msg.sender === userData.username ? "justify-end" : "justify-start"
          }`}
        >
          <MessageBubble
            message={msg}
            isFromCurrentUser={msg.sender === userData.username}
          />
        </div>
      ))}
    </div>
  );
};

export default MessageList;
