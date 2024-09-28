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

export default function RegisterModal({
  isOpen,
  onOpen,
  onOpenChange,
  setUserData,
}) {
  const [registrationData, setRegistrationData] = useState({
    username: "",
    password: "",
    firstName: "",
    lastName: "",
  });
  const apiClient = useAxios();

  const register = async () => {
    let response;
    try {
      response = await apiClient.post("/auth/register", registrationData);

      console.log("Axios response: " + response.headers);

      const token = response.headers["authorization"];

      if (token) {
        console.log("Set New Acess Token:" + response.headers);
        const tokenPart = token.split(" ")[1];
        localStorage.setItem("accessToken", tokenPart);
        setUserData(response.data);
        return true;
      }
    } catch (error) {
      console.log(error);
    }
  };

  return (
    <>
      <Modal isOpen={isOpen} onOpenChange={onOpenChange} placement="top-center">
        <ModalContent>
          {(onClose) => (
            <>
              <ModalHeader className="flex flex-col gap-1">
                Register
              </ModalHeader>
              <ModalBody>
                <Input
                  autoFocus
                  label="First Name"
                  placeholder="John"
                  variant="bordered"
                  onChange={(e) => {
                    // console.log(e.target.value);
                    setRegistrationData((state) => ({
                      ...state,
                      firstName: e.target.value,
                    }));
                  }}
                />

                <Input
                  label="Last Name"
                  placeholder="Doe"
                  variant="bordered"
                  onChange={(e) => {
                    // console.log(e.target.value);
                    setRegistrationData((state) => ({
                      ...state,
                      lastName: e.target.value,
                    }));
                  }}
                />
                <Input
                  endContent={
                    <MailIcon className="text-2xl text-default-400 pointer-events-none flex-shrink-0" />
                  }
                  label="Username"
                  placeholder="Enter your username"
                  variant="bordered"
                  autoComplete="username"
                  onChange={(e) => {
                    // console.log(e.target.value);
                    setRegistrationData((state) => ({
                      ...state,
                      username: e.target.value,
                    }));
                  }}
                />
                <Input
                  endContent={
                    <LockIcon className="text-2xl text-default-400 pointer-events-none flex-shrink-0" />
                  }
                  label="Password"
                  placeholder="Enter your password"
                  type="password"
                  autoComplete="password"
                  variant="bordered"
                  onChange={(e) => {
                    // console.log(e.target.value);
                    setRegistrationData((state) => ({
                      ...state,
                      password: e.target.value,
                    }));
                  }}
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
                    register().then(
                      (registerStatus) => registerStatus && onClose()
                    );
                  }}
                >
                  Register
                </Button>
              </ModalFooter>
            </>
          )}
        </ModalContent>
      </Modal>
    </>
  );
}
