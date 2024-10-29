import React, { useState } from "react";
import {
  User,
  Input,
  Divider,
  Listbox,
  Card,
  CardBody,
  Select,
  SelectItem,
  Button,
} from "@nextui-org/react";
import useSocket from "../../WebSockets/websocketClient";
import useUser from "../../Security/hooks/useUser";
import MessageList from "./MessageList";

const mockUsers = [
  {
    id: 122,
    username: "abc",
    firstname: "abc",
    lastname: "abc",
    pfp: "https://i.pravatar.cc/150?u=alice",
  },
  {
    id: 2,
    username: "bob_jones",
    firstname: "Bob",
    lastname: "Jones",
    pfp: "https://i.pravatar.cc/150?u=bob",
  },
  {
    id: 3,
    username: "carol_wilson",
    firstname: "Carol",
    lastname: "Wilson",
    pfp: "https://i.pravatar.cc/150?u=carol",
  },
];

const MessageBox = () => {
  const [selectedUser, setSelectedUser] = useState(null);
  const [messageInput, setMessageInput] = useState("");
  const token = localStorage.getItem("accessToken");
  const [userData] = useUser();
  const { messages, sendMessage, connected, error, adminLogs, activeUsers } =
    useSocket(token, userData.username, false);

  const handleSendMessage = () => {
    if (!selectedUser || !messageInput.trim()) return;
    const sent = sendMessage(selectedUser.username, messageInput.trim());
    if (sent) setMessageInput("");
  };

  const handleSelectionChange = (userId) => {
    const selected = mockUsers.find((user) => user.id === Number(userId));
    setSelectedUser(selected);
  };

  return (
    <div className="p-8 flex flex-col items-center max-w-2xl mx-auto">
      <Card className="w-full">
        <CardBody className="items-center">
          <h2 className="text-xl font-bold mb-4">Message Center</h2>
          {!connected && (
            <div className="text-red-500 mb-2">
              Disconnected from chat server...
            </div>
          )}
          {error && <div className="text-red-500 mb-2">Error: {error}</div>}
        </CardBody>
        <CardBody className="w-full items-center">
          <Select
            className="w-full mb-5"
            label="Select Recipient"
            placeholder="Choose who to message"
            selectedKeys={selectedUser ? [selectedUser.id.toString()] : []}
            onChange={(e) => handleSelectionChange(e.target.value)}
          >
            {mockUsers.map((user) => (
              <SelectItem
                key={user.id}
                value={user.id}
                textValue={`${user.firstname} ${user.lastname}`}
              >
                <div className="flex gap-2 items-center">
                  <User
                    name={`${user.firstname} ${user.lastname}`}
                    description={user.username}
                    avatarProps={{
                      src: user.pfp,
                      alt: `${user.firstname} ${user.lastname}'s profile`,
                    }}
                  />
                </div>
              </SelectItem>
            ))}
          </Select>
        </CardBody>
      </Card>

      <Card className="mt-4 w-full">
        <CardBody className="p-4 h-[400px] overflow-y-auto">
          <MessageList messages={messages} userData={userData} />
        </CardBody>
        <Divider />
        <CardBody className="flex items-center gap-4 p-4">
          <Input
            className="flex-grow rounded-md"
            placeholder="Type your message here..."
            value={messageInput}
            onChange={(e) => setMessageInput(e.target.value)}
            onKeyPress={(e) => e.key === "Enter" && handleSendMessage()}
            disabled={!connected}
          />
          <Button
            onClick={handleSendMessage}
            disabled={!selectedUser || !messageInput.trim() || !connected}
            color="primary"
          >
            Send
          </Button>
        </CardBody>
      </Card>
    </div>
  );
};

export default MessageBox;
