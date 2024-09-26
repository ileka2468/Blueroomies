import React, { createContext, useContext } from "react";
import apiClient from "./apiClient";

const AxiosContext = createContext();

export const AxiosProvider = ({ children }) => {
  return (
    <AxiosContext.Provider value={apiClient}>{children}</AxiosContext.Provider>
  );
};

export const useAxios = () => {
  return useContext(AxiosContext);
};
