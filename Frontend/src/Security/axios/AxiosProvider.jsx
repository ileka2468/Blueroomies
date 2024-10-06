import React, { createContext, useContext } from "react";
import apiClient from "./apiClient";
import PropTypes from "prop-types";

const AxiosContext = createContext();

export const AxiosProvider = ({ children }) => {
  return (
    <AxiosContext.Provider value={apiClient}>{children}</AxiosContext.Provider>
  );
};

AxiosProvider.propTypes = {
  children: PropTypes.node.isRequired, 
};

export const useAxios = () => {
  return useContext(AxiosContext);
};
