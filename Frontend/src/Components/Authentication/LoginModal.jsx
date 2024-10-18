import {
  Modal,
  ModalContent,
  ModalHeader,
  ModalBody,
  ModalFooter,
  Button,
  Checkbox,
  Input,
  Link,
} from "@nextui-org/react";
import { MailIcon } from "./MailIcon.jsx";
import { LockIcon } from "./LockIcon.jsx";
import { useState } from "react";
import { useAxios } from "../../Security/axios/AxiosProvider.jsx";
import PropTypes from "prop-types";

export default function LoginModal({
  isOpen,
  onOpen,
  onOpenChange,
  setUserData,
  isUser,
}) {
  const [user, setUsername] = useState("");
  const [pass, setPassword] = useState("");

  const apiClient = useAxios();

  const handleLogin = async () => {
    let response;
    try {
      response = await apiClient.post("auth/login", {
        username: user,
        password: pass,
      });

      const token = response.headers["authorization"];

      if (token) {
        const tokenPart = token.split(" ")[1];
        localStorage.setItem("accessToken", tokenPart);
        setUserData(response.data);
        return true;
      }
    } catch (error) {
      console.log(error);
    }
    return false;
  };

  return (
    <>
      <Modal isOpen={isOpen} onOpenChange={onOpenChange} placement="top-center">
        <ModalContent>
          {(onClose) => (
            <>
              <ModalHeader className="flex flex-col gap-1">Log in</ModalHeader>
              <ModalBody>
                <Input
                  autoFocus
                  endContent={
                    <MailIcon
                      size="1em"
                      className="text-2xl text-default-400 pointer-events-none flex-shrink-0"
                    />
                  }
                  label="Email"
                  placeholder="Enter your email"
                  variant="bordered"
                  value={user}
                  onChange={(e) => setUsername(e.target.value)}
                />
                <Input
                  endContent={
                    <LockIcon className="text-2xl text-default-400 pointer-events-none flex-shrink-0" />
                  }
                  label="Password"
                  placeholder="Enter your password"
                  type="password"
                  variant="bordered"
                  value={pass}
                  onChange={(e) => setPassword(e.target.value)}
                />
                <div className="flex py-2 px-1 justify-between">
                  <Checkbox
                    classNames={{
                      label: "text-small",
                    }}
                  >
                    Remember me
                  </Checkbox>
                  <Link color="primary" href="#" size="sm">
                    Forgot password?
                  </Link>
                </div>
              </ModalBody>
              <ModalFooter>
                <Button color="danger" variant="flat" onPress={onClose}>
                  Close
                </Button>
                <Button
                  color="primary"
                  onPress={() => {
                    handleLogin().then((status) => {
                      status && onClose();
                    });
                  }}
                >
                  Sign in
                </Button>
              </ModalFooter>
            </>
          )}
        </ModalContent>
      </Modal>
    </>
  );
}

LoginModal.propTypes = {
  isOpen: PropTypes.bool.isRequired,
  onOpenChange: PropTypes.func.isRequired,
  setUserData: PropTypes.func.isRequired,
  isUser: PropTypes.bool.isRequired, // Prop validation
};
