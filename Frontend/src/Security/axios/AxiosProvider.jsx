import React, { createContext, useContext } from "react";
import apiClient from "./apiClient";

// Create a Context for the Axios client
const AxiosContext = createContext();

// Create a Provider component to pass down the Axios instance
export const AxiosProvider = ({ children }) => {
  return (
    <AxiosContext.Provider value={apiClient}>{children}</AxiosContext.Provider>
  );
};

// Custom hook to use the Axios client in any component
export const useAxios = () => {
  return useContext(AxiosContext);
};
