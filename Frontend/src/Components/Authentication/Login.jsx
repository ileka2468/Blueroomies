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
      console.log("Axios response: " + response.status);
    } catch (error) {
      console.log(error);
    }
  };

  return (
    <>
      <form onSubmit={(e) => handleLogin(e)}>
        <label htmlFor="username">Username</label>
        <input
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          type="text"
          name="username"
        />

        <label htmlFor="passowrd">Password</label>
        <input
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          type="password"
          name="password"
        />

        <button type="submit">Login</button>
      </form>
    </>
  );
};

export default Login;
