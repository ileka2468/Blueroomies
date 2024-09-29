import { useAxios } from "./Security/axios/AxiosProvider";
import Nav from "./Components/Dashboard/Nav/Nav";
import { useDisclosure } from "@nextui-org/react";
import LoginModal from "./Components/Authentication/LoginModal";
import RegisterModal from "./Components/Authentication/RegisterModal";
import useUser from "./Security/hooks/useUser";
import SideFilter from "./Components/Filter/SideFilter";

function App() {
  const apiClient = useAxios();
  const [userData, setUserData, isUser] = useUser();

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
    <main className="h-full px-4">
      <>
        {/* Login Modal */}
        <LoginModal
          isOpen={isLoginOpen}
          onOpen={onLoginOpen}
          onOpenChange={onLoginOpenChange}
          setUserData={setUserData}
          isUser={isUser}
        />

        {/* Register Modal */}
        <RegisterModal
          isOpen={isRegisterOpen}
          onOpen={onRegisterOpen}
          onOpenChange={onRegisterOpenChange}
          setUserData={setUserData}
        />

        <Nav
          onRegisterOpen={onRegisterOpen}
          onLoginOpen={onLoginOpen}
          userData={userData}
          setUserData={setUserData}
          isUser={isUser}
        />
        <div className="flex h-[90%]">
          {/* Side Filter */}
          <SideFilter />
          {/* Roommate Results */}
          <div className="flex-grow p-4">
            <h2 className="text-lg font-bold">Roommate Results</h2>
            {/* result component here */}
          </div>
        </div>
      </>
    </main>
  );
}

export default App;
