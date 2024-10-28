import Nav from "../Nav/Nav";
import LoginModal from "../Authentication/LoginModal";
import RegisterModal from "../Authentication/RegisterModal";
import { useDisclosure } from "@nextui-org/react";
import useUser from "../../Security/hooks/useUser";
import { Outlet } from "react-router-dom";

export default function Root() {
  const [userData, setUserData, isUser] = useUser();
  
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

  return (
    <>
      <main className="h-full px-4 pt-16">
        <LoginModal
          isOpen={isLoginOpen}
          onOpen={onLoginOpen}
          onOpenChange={onLoginOpenChange}
          setUserData={setUserData}
          isUser={isUser}
        />
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
        
        <Outlet context={{ isUser }} /> {/* Pass isUser to child routes */}
      </main>
    </>
  );
}
