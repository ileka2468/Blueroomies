import "./App.css";
import { useAxios } from "./Security/axios/AxiosProvider";
import Nav from "./Components/Dashboard/Nav/Nav";
import { useDisclosure } from "@nextui-org/react";
import LoginModal from "./Components/Authentication/LoginModal";

function App() {
  const apiClient = useAxios();
  const { isOpen, onOpen, onOpenChange } = useDisclosure();

  const getProtectedData = async () => {
    try {
      const data = await apiClient.post("/matches/run");
      console.log(data);
    } catch (e) {
      console.log(e);
    }
  };

  const logout = async () => {
    const response = await apiClient.post("/auth/logout");
    console.log(response?.status);
  };

  const refreshToken = async () => {
    const response = await apiClient.post("/auth/refresh-token");
    console.log(response?.status);
  };

  return (
    <>
      <Nav onOpen={onOpen}></Nav>
      <LoginModal
        isOpen={isOpen}
        onOpen={onOpen}
        onOpenChange={onOpenChange}
      ></LoginModal>
    </>
  );
}

export default App;
