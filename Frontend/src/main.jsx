import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import App from "./App.jsx";
import "./index.css";
import { AxiosProvider } from "./Security/axios/AxiosProvider.jsx";

createRoot(document.getElementById("root")).render(
  <StrictMode>
    <Router>
    <AxiosProvider>
      <App />
    </AxiosProvider>
    </Router>
  </StrictMode>
);
