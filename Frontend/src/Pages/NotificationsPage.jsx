import React from "react";
import { useAxios } from "../Security/axios/AxiosProvider";
import { useState } from "react";
import { useMemo } from "react";
import NotificationBox from "../Components/Notifications/NotificationBox.jsx";

const NotificationsPage = () => {
    return (
        <div>
            <NotificationBox />
        </div>
    );
};

export default NotificationsPage;