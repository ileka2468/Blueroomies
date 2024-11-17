import React, { useEffect, useState } from "react";
import {
  User,
  Input,
  Divider,
  Listbox,
  ListboxItem,
  Card,
  CardBody,
  Select,
  SelectItem,
  ScrollShadow,
  Button,
} from "@nextui-org/react";
import { users } from "./data";
import useUser from "../../Security/hooks/useUser";
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

const AgreementBox = () => {
  const apiClient = useAxios();
  const [matchdata, setMatchData] = useState([]);
  const [loadingMatches, setLoadingMatches] = useState(true);
  const [selectedUser, setSelectedUser] = useState(null);
  const [loadingAgreement, setLoadingAgreement] = useState(false);
  const [agreementData, setAgreementData] = useState([]);
  const [agreementInput, setAgreementInput] = useState("");
  const [agreementExists, setAgreementExists] = useState(false);
  const [isModifying, setIsModifying] = useState(false);
  const [isSender, setIsSender] = useState(false);
  const [agreementState, setAgreementState] = useState(2);
  const [agreementStateText, setAgreementStateText] = useState(["This agreement is Declined", "This agreement is Accepted", "This agreement is Pending"]);

  const [agreementActionData, setAgreementActionData] = useState({
    otherUsername: ""
  });

  const [agreementCreateData, setAgreementCreateData] = useState({
    content: "",
    receiver: ""
  });

  useEffect(() => {
    const initAgreementData = async () => {
      setAgreementData(["initialData"]);
      //const response = await apiClient.get("/agreements/check");
      //setAgreementExists(response.data);
    };
    //initAgreementData();
  }, [apiClient]);

  //Grabs match data for each matched user
  useEffect(() => {
    const fetchMatches = async () => {
      setLoadingMatches(true);
      const matches = await getMatches(apiClient);
      setMatchData(matches);
      setLoadingMatches(false);
    };
    fetchMatches();
  }, [apiClient]);

  //Handle loading an agreement between matched users.
  useEffect(() => {
    const loadAgreement = async () => {
      if (selectedUser) {
        setLoadingAgreement(true);
        try {
          const response = await apiClient.get("/agreements/check", {
            params: { otherUser: selectedUser.username },
          });
          setAgreementExists(response.data);
        } catch (error) {
          console.error("Error checking agreement:", error);
        } finally {
          setLoadingAgreement(false);
        }

        setLoadingAgreement(true);
        try {
          const response = await apiClient.get("/agreements/checkSender", {
            params: { otherUser: selectedUser.username },
          });
          setIsSender(response.data);
        } catch (error) {
          console.error("Error checking agreement sender:", error);
        } finally {
          setLoadingAgreement(false);
        }

        setLoadingAgreement(true);
        try {
          const response = await apiClient.get("/agreements/view", {
            params: { otherUser: selectedUser.username },
          });
          setAgreementData(response.data);
        } catch (error) {
          console.error("Error fetching agreement:", error);
        } finally {
          setLoadingAgreement(false);
        }

        setLoadingAgreement(true);
        try {
          const response = await apiClient.get("/agreements/status", {
            params: { otherUser: selectedUser.username },
          });
          setAgreementState(response.data);
        } catch (error) {
          console.error("Error fetching agreement status:", error);
        } finally {
          setLoadingAgreement(false);
        }

      } else {
        // No user selected, ensure loadingAgreements is false
        setLoadingAgreement(false);
      }
    };
    loadAgreement();
  }, [apiClient, selectedUser])

  //Handle saving an agreement between matched users when save button is pressed.
  const handleSave = async () => {
    agreementCreateData.content = agreementInput;
    agreementCreateData.receiver = selectedUser.username;

    setLoadingAgreement(true);
    try {
      const response = await apiClient.post("/agreements/create", agreementCreateData);
    } catch (error) {
      console.error("Error creating agreement:", error);
    } finally {
      setLoadingAgreement(false);
    }

    if (selectedUser) {
      setLoadingAgreement(true);
      try {
        const response = await apiClient.get("/agreements/check", {
          params: { otherUser: selectedUser.username },
        });
        setAgreementExists(response.data);
      } catch (error) {
        console.error("Error checking agreement:", error);
      } finally {
        setLoadingAgreement(false);
      }

      setLoadingAgreement(true);
        try {
          const response = await apiClient.get("/agreements/checkSender", {
            params: { otherUser: selectedUser.username },
          });
          setIsSender(response.data);
        } catch (error) {
          console.error("Error checking agreement sender:", error);
        } finally {
          setLoadingAgreement(false);
        }

      setLoadingAgreement(true);
      try {
        const response = await apiClient.get("/agreements/view", {
          params: { otherUser: selectedUser.username },
        });
        setAgreementData(response.data);
      } catch (error) {
        console.error("Error fetching agreement:", error);
      } finally {
        setLoadingAgreement(false);
      }

      setLoadingAgreement(true);
        try {
          const response = await apiClient.get("/agreements/status", {
            params: { otherUser: selectedUser.username },
          });
          setAgreementState(response.data);
        } catch (error) {
          console.error("Error fetching agreement status:", error);
        } finally {
          setLoadingAgreement(false);
        }

    } else {
      // No user selected, ensure loadingAgreements is false
      setLoadingAgreement(false);
    }

  };

  //Update selectedUser when selection is changed
  const handleSelectionChange = (userId) => {
    const selected = matchdata.find((user) => user.id === Number(userId));
    setSelectedUser(selected);
    if (selected) {
      setLoadingAgreement(true);
    } else {
      setLoadingAgreement(false);
    }
  };

  const handleSendAgreement = (userId) => {
    if(!selectedUser){
      return;
    }
  };

  //Enter modifying state when modify button is pressed
  const handleModify = () => {
    setIsModifying(true);
  };

  //Handles user pressing the reject button. Sets agreement state to 0
  const handleReject = async () => {
    setLoadingAgreement(true);
    try {
      agreementActionData.otherUsername = selectedUser.username;
      const response = await apiClient.post("/agreements/reject", agreementActionData);
      setAgreementState(0);
    } catch (error) {
      console.error("Error rejecting agreement:", error);
    } finally {
      setLoadingAgreement(false);
    }
    setLoadingAgreement(false);
  };

  //Handles user pressing the accept button. Sets agreement state to 1
  const handleAccept = async () => {
    setLoadingAgreement(true);
    try {
      agreementActionData.otherUsername = selectedUser.username;
      const response = await apiClient.post("/agreements/accept", agreementActionData);
      setAgreementState(1);
    } catch (error) {
      console.error("Error accepting agreement:", error);
    } finally {
      setLoadingAgreement(false);
    }
    setLoadingAgreement(false);
  };

  return (
    <div>
      <Card>
        <CardBody className="items-center">Agreement Center</CardBody>
        <CardBody className="items-center">
          <Select
            aria-label="idk"
            className="w-[500px] pb-5"
            items={users}
            placeholder="Select a User"
            selectedKeys={selectedUser ? [selectedUser.id.toString()] : []}
            onChange={(e) => handleSelectionChange(e.target.value)}
            isLoading={loadingMatches || loadingAgreement}
            isDisabled={loadingMatches || loadingAgreement}
            >{matchdata.map((user) => (
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
            {(user) => (
              <SelectItem aria-label="idk" key={user.id}>
                <User name={user.name} avatarProps={user.avatarProps} />
              </SelectItem>
            )}
          </Select>
        </CardBody>
      </Card>

      <Card>
        <CardBody className="items-center">
          <ScrollShadow>
            <Listbox aria-label="idk" className="w-[500px] h-[400px] size-fit py-8">
               {/*<ListboxItem>This is an agreement.</ListboxItem> */}
               : selectedUser ? (
               <ListboxItem> {agreementData.content} </ListboxItem>
               )
            </Listbox>
            <p>{agreementStateText[agreementState]}</p>
            <Input isDisabled={!isModifying || loadingAgreement} 
            value={agreementInput}
            onChange={(e) => setAgreementInput(e.target.value)}/>
          </ScrollShadow>
        </CardBody>
        <Divider />
        <CardBody className="items-center">
          <div className="grid grid-cols-7">
            <Button isDisabled={!selectedUser || !agreementExists || isSender || loadingAgreement || agreementState == 1}
            onPress={handleAccept}
            variant="solid" color="success">Accept</Button>
            <div></div>
            <Button isDisabled={!selectedUser || !agreementExists || isSender || loadingAgreement || agreementState == 0}
            onPress={handleReject}
            variant="solid" color="danger">Decline</Button>
            <div></div>
            <Button isDisabled={!selectedUser || loadingAgreement}
            onPress={handleModify}
            variant="solid" color="primary">Modify</Button>
            <div></div>
            <Button isDisabled={!selectedUser || !isModifying || loadingAgreement}
            onPress={handleSave}
            variant="solid" color="primary">Save</Button>
          </div>
        </CardBody>
      </Card>
    </div>
  );
};

export default AgreementBox;
