import React from "react";
import { useAxios } from "../Security/axios/AxiosProvider";
import { useState } from "react";
import { useMemo } from "react";
import AgreementBox from "../Components/Agreements/AgreementBox.jsx";

const AgreementsPage = () => {
    return (
        <div>
            <AgreementBox />
        </div>
    );
};

export default AgreementsPage;