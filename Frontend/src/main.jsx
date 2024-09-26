import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import App from "./App.jsx";
import "./index.css";
import { AxiosProvider } from "./Security/axios/AxiosProvider.jsx";
import { NextUIProvider } from "@nextui-org/react";
import Nav from "./Components/Dashboard/Nav/Nav.jsx";

createRoot(document.getElementById("root")).render(
  <AxiosProvider>
    <NextUIProvider>
      <StrictMode>
        <App />
      </StrictMode>
    </NextUIProvider>
  </AxiosProvider>
);
