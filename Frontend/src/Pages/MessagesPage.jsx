import React from "react";
import { useAxios } from "../Security/axios/AxiosProvider";
import { useState } from "react";
import { useMemo } from "react";
import MessageBox from "../Components/Messages/MessageBox.jsx";

const MessagesPage = () => {
    return (
        <div>
            <MessageBox />
        </div>
    );
};

export default MessagesPage;