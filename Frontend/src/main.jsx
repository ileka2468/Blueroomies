import { StrictMode } from "react";
import ReactDOM from 'react-dom/client';
import { BrowserRouter as Router } from 'react-router-dom';
import App from "./App.jsx";
import "./index.css";
import { AxiosProvider } from "./Security/axios/AxiosProvider.jsx";

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  
  <StrictMode>
    <Router>
      <AxiosProvider>
        <App />
      </AxiosProvider>
    </Router>
  </StrictMode>
);