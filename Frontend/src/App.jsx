import { useAxios } from "./Security/axios/AxiosProvider";
import Nav from "./Components/Dashboard/Nav/Nav";
import { useDisclosure } from "@nextui-org/react";
import LoginModal from "./Components/Authentication/LoginModal";
import RegisterModal from "./Components/Authentication/RegisterModal";
import useUser from "./Security/hooks/useUser";

function App() {
  const apiClient = useAxios();
  const [userData, setUserData] = useUser();

  // useDisclosure for LoginModal
  const {
    isOpen: isLoginOpen,
    onOpen: onLoginOpen,
    onOpenChange: onLoginOpenChange,
  } = useDisclosure();

  const {
    isOpen: isRegisterOpen,
    onOpen: onRegisterOpen,
    onOpenChange: onRegisterOpenChange,
  } = useDisclosure();

  const getProtectedData = async () => {
    try {
      const data = await apiClient.post("/matches/run");
      console.log(data);
    } catch (e) {
      console.log(e);
    }
  };

  return (
    <>
      <Nav
        onRegisterOpen={onRegisterOpen}
        onLoginOpen={onLoginOpen}
        userData={userData}
      ></Nav>

      {/* Login Modal */}
      <LoginModal
        isOpen={isLoginOpen}
        onOpen={onLoginOpen}
        onOpenChange={onLoginOpenChange}
      ></LoginModal>

      {/* Register Modal */}
      <RegisterModal
        isOpen={isRegisterOpen}
        onOpen={onRegisterOpen}
        onOpenChange={onRegisterOpenChange}
        setUserData={setUserData}
      ></RegisterModal>
    </>
  );
}

export default App;
