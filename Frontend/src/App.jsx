import reactLogo from "./assets/react.svg";
import viteLogo from "/vite.svg";
import "./App.css";
import { useAxios } from "./Security/axios/AxiosProvider";
import Login from "./Components/Authentication/Login";

function App() {
  const apiClient = useAxios();

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
      <div>
        <a href="https://vitejs.dev" target="_blank">
          <img src={viteLogo} className="logo" alt="Vite logo" />
        </a>
        <a href="https://react.dev" target="_blank">
          <img src={reactLogo} className="logo react" alt="React logo" />
        </a>
      </div>
      <h1>Roomate Finding App</h1>

      <Login></Login>

      <button onClick={() => getProtectedData()}>Get Authed Data</button>
      <button onClick={() => logout()}>Logout</button>
      <button onClick={() => refreshToken()}>Refresh Token</button>
    </>
  );
}

export default App;
