import React from "react";
import { useState } from "react";
import { useAxios } from "../../Security/axios/AxiosProvider";

const Login = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const apiClient = useAxios();

  const handleLogin = async (e) => {
    e.preventDefault();

    try {
      const response = await apiClient.post("/auth/login", {
        username: username,
        password: password,
      });
      console.log("Axios response: " + response.headers);

      const token = response.headers["authorization"];

      if (token) {
        console.log("Set New Acess Token:" + response.headers);
        const tokenPart = token.split(" ")[1];
        localStorage.setItem("accessToken", tokenPart);
      }
    } catch (error) {
      console.log(error);
    }
  };

  return (
    <>
      <form onSubmit={(e) => handleLogin(e)}>
        <div>
          <label htmlFor="username">Username</label>
          <input
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            type="text"
            name="username"
            className=""
          />
        </div>

        <div className="mt-2">
          <label htmlFor="passowrd">Password</label>
          <input
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            type="password"
            name="password"
          />
        </div>
      </form>
    </>
  );
};

export default Login;
