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
  CircularProgress,
} from "@nextui-org/react";
import useSocket from "../../WebSockets/webSocketClient";
import useUser from "../../Security/hooks/useUser";
import MessageList from "./MessageList";
import { useAxios } from "../../Security/axios/AxiosProvider";
import { useLocation } from "react-router-dom";

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
  const [loadingMatches, setLoadingMatches] = useState(true);
  const [selectedUser, setSelectedUser] = useState(null);
  const [messageInput, setMessageInput] = useState("");
  const [conversationMessages, setConversationMessages] = useState([]);
  const [loadingMessages, setLoadingMessages] = useState(false);
  const token = localStorage.getItem("accessToken");
  const location = useLocation();
  const userParam = new URLSearchParams(location.search).get("user");
  console.log(userParam);
  const [userData] = useUser();

  const {
    messages: liveMessages,
    sendMessage,
    connected,
    error,
    appendLocalMessageOnSuccess,
  } = useSocket(token, userData.username, selectedUser, false);

  useEffect(() => {
    const fetchMatches = async () => {
      setLoadingMatches(true);
      const matches = await getMatches(apiClient);
      setMatchData(matches);
      setLoadingMatches(false);
    };
    fetchMatches();
  }, [apiClient]);

  useEffect(() => {
    const loadConversation = async () => {
      if (selectedUser) {
        setLoadingMessages(true);
        try {
          const response = await apiClient.get("/messages/conversation", {
            params: { otherUser: selectedUser.username },
          });
          setConversationMessages(response.data);
        } catch (error) {
          console.error("Error fetching conversation messages:", error);
        } finally {
          setLoadingMessages(false);
        }
      } else {
        // No user selected, ensure loadingMessages is false
        setLoadingMessages(false);
      }
    };
    loadConversation();
  }, [apiClient, selectedUser]);

  useEffect(() => {
    if (liveMessages.length > 0) {
      const latestLiveMessage = liveMessages[liveMessages.length - 1];
      setConversationMessages((prevMessages) => [
        ...prevMessages,
        latestLiveMessage,
      ]);
    }
  }, [liveMessages]);

  useEffect(() => {
    if (userParam && matchdata.length > 0) {
      const matchedUser = matchdata.find(
        (user) => user.id === Number(userParam)
      );
      setSelectedUser(matchedUser || null);
    }
  }, [userParam, matchdata]);

  const handleSendMessage = () => {
    if (!selectedUser || !messageInput.trim()) return;
    const sent = sendMessage(selectedUser.username, messageInput.trim());
    if (sent) {
      appendLocalMessageOnSuccess(messageInput.trim());
      setMessageInput("");
    }
  };

  const handleSelectionChange = (userId) => {
    const selected = matchdata.find((user) => user.id === Number(userId));
    setSelectedUser(selected);
    setConversationMessages([]);
    if (selected) {
      setLoadingMessages(true);
    } else {
      setLoadingMessages(false);
    }
  };

  return (
    <div className="p-8 flex flex-col items-center max-w-2xl mx-auto">
      {connected ? (
        // Render the message center when connected
        <>
          <Card className="w-full">
            <CardBody className="items-center">
              <h2 className="text-xl font-bold mb-4">Message Center</h2>
              {/* Remove the disconnected message */}
              {error && <div className="text-red-500 mb-2">Error: {error}</div>}
            </CardBody>
            <CardBody className="w-full items-center">
              <Select
                className="w-full mb-5"
                label="Select Recipient"
                placeholder="Choose who to message"
                selectedKeys={selectedUser ? [selectedUser.id.toString()] : []}
                onChange={(e) => handleSelectionChange(e.target.value)}
                isLoading={loadingMatches}
                isDisabled={loadingMatches}
              >
                {[
                  ...new Map(matchdata.map((user) => [user.id, user])).values(),
                ].map((user) => (
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
              {loadingMessages ? (
                <div className="flex justify-center items-center h-full">
                  <CircularProgress color="primary" />
                </div>
              ) : selectedUser ? (
                <MessageList
                  messages={conversationMessages}
                  userData={userData}
                />
              ) : (
                <div className="flex justify-center items-center h-full text-gray-500">
                  Please select a user to view messages.
                </div>
              )}
            </CardBody>
            <Divider />
            <CardBody className="flex items-center gap-4 p-4">
              <Input
                className="flex-grow rounded-md"
                placeholder="Type your message here..."
                value={messageInput}
                onChange={(e) => setMessageInput(e.target.value)}
                onKeyPress={(e) => e.key === "Enter" && handleSendMessage()}
                disabled={!connected || loadingMessages || !selectedUser}
              />
              <Button
                onClick={handleSendMessage}
                disabled={
                  !selectedUser ||
                  !messageInput.trim() ||
                  !connected ||
                  loadingMessages
                }
                className="bg-messageblue text-white"
              >
                Send
              </Button>
            </CardBody>
          </Card>
        </>
      ) : error ? (
        <div className="text-red-500 mb-2">
          Error connecting to chat server: {error}
        </div>
      ) : (
        // Show loading spinner while connecting
        <div className="flex flex-col items-center">
          <CircularProgress color="primary" />
          <p className="mt-4 text-gray-500">Loading...</p>
        </div>
      )}
    </div>
  );
};

export default MessageBox;
