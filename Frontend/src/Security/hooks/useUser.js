import { useEffect, useState } from "react";
import { useAxios } from "../axios/AxiosProvider";

const useUser = () => {
  const apiClient = useAxios();
  const [userData, setUserData] = useState({
    username: "",
    firstname: "",
    lastname: "",
    pfp: "",
  });

  const [isUser, setIsUser] = useState(false);

  useEffect(() => {
    const fetchUser = async () => {
      if (localStorage.getItem("accessToken") != null) {
        const response = await apiClient.get("/auth/user");
        if (response.status === 200) {
          setUserData(response.data);
        }
      }
    };
    fetchUser();
  }, []);

  useEffect(() => {
    if (userData.username) {
      setIsUser(true);
    } else {
      setIsUser(false);
    }
  }, [userData]);

  return [userData, setUserData, isUser];
};

export default useUser;
