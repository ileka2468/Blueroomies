import MessageBubble from "./MessageBubble";

const MessageList = ({ messages, userData }) => {
  console.log(userData, messages);
  return (
    <div className="p-4 flex flex-col gap-2 h-[400px] overflow-y-auto">
      {messages.map((msg, index) => (
        <MessageBubble
          key={index}
          message={msg}
          isFromCurrentUser={msg.from === userData.username}
        />
      ))}
    </div>
  );
};

export default MessageList;
