import { useEffect, useState } from "react";
import React from "react";

const useUser = () => {
  const [userData, setUserData] = useState({
    username: "",
    firstname: "",
    lastname: "",
    pfp: "",
  });

  useEffect(() => {
    console.log(userData);
  }, [userData]);

  return [userData, setUserData];
};

export default useUser;
