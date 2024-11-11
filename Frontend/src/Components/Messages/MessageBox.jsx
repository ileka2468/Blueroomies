import React, { useEffect, useState } from "react";
import {
  User,
  Input,
  Divider,
  Card,
  CardBody,
  Select,
  SelectItem,
  Button,
} from "@nextui-org/react";
import useSocket from "../../WebSockets/webSocketClient";
import useUser from "../../Security/hooks/useUser";
import MessageList from "./MessageList";
import { useAxios } from "../../Security/axios/AxiosProvider";

const getMatches = async (apiClient) => {
  try {
    const response = await apiClient.get("/matches/me");
    return response.data.matches || [];
  } catch (error) {
    console.error("Error fetching matches:", error);
    return [];
  }
};

const MessageBox = () => {
  const apiClient = useAxios();
  const [matchdata, setMatchData] = useState([]);
  const [selectedUser, setSelectedUser] = useState(null);
  const [messageInput, setMessageInput] = useState("");
  const [conversationMessages, setConversationMessages] = useState([]); // DB messages loaded once
  const token = localStorage.getItem("accessToken");
  const [userData] = useUser();

  // WebSocket live messages
  const {
    messages: liveMessages,
    sendMessage,
    connected,
    error,
    appendLocalMessageOnSuccess,
  } = useSocket(token, userData.username, selectedUser, false);

  useEffect(() => {
    const fetchMatches = async () => {
      const matches = await getMatches(apiClient);
      setMatchData(matches);
    };
    fetchMatches();
  }, [apiClient]);

  // Load database messages only once when `selectedUser` changes
  useEffect(() => {
    const loadConversation = async () => {
      if (selectedUser) {
        try {
          const response = await apiClient.get("/messages/conversation", {
            params: { otherUser: selectedUser.username },
          });
          setConversationMessages(response.data); // Set DB messages
        } catch (error) {
          console.error("Error fetching conversation messages:", error);
        }
      }
    };
    loadConversation();
  }, [apiClient, selectedUser]);

  // Append live messages to `conversationMessages`
  useEffect(() => {
    if (liveMessages.length > 0) {
      const latestLiveMessage = liveMessages[liveMessages.length - 1];
      setConversationMessages((prevMessages) => [
        ...prevMessages,
        latestLiveMessage,
      ]);
    }
  }, [liveMessages]);

  const handleSendMessage = () => {
    if (!selectedUser || !messageInput.trim()) return;
    const sent = sendMessage(selectedUser.username, messageInput.trim());
    if (sent) {
      appendLocalMessageOnSuccess(messageInput.trim());
      setMessageInput("");

      // setConversationMessages((prevMessages) => [
      //   ...prevMessages,
      //   {
      //     sender: userData.username,
      //     receiver: selectedUser.username,
      //     content: messageInput.trim(),
      //     timestamp: new Date(),
      //   },
      // ]);
    }
  };

  // Handle selection change to load the selected user's conversation
  const handleSelectionChange = async (userId) => {
    const selected = matchdata.find((user) => user.id === Number(userId));
    setSelectedUser(selected);

    // Clear previous messages and load new conversation
    setConversationMessages([]);
    if (selected) {
      try {
        const response = await apiClient.get("/messages/conversation", {
          params: { otherUser: selected.username },
        });
        setConversationMessages(response.data); // Load selected user's messages
      } catch (error) {
        console.error("Error fetching conversation messages:", error);
      }
    }
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
            {matchdata.map((user) => (
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
          <MessageList messages={conversationMessages} userData={userData} />
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
            className="bg-messageblue text-white"
          >
            Send
          </Button>
        </CardBody>
      </Card>
    </div>
  );
};

export default MessageBox;
