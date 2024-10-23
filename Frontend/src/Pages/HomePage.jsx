import React, { useState, useEffect } from "react";
import { useOutletContext } from "react-router-dom";
import PreferencesForm from "../Components/Preferences/PreferenceForm.jsx"; // Make sure the path is correct

const HomePage = () => {
  const { isUser } = useOutletContext();
  const [isMobile, setIsMobile] = useState(window.innerWidth < 768); // Mobile detection

  useEffect(() => {
    const handleResize = () => {
      setIsMobile(window.innerWidth < 768);
    };

    window.addEventListener("resize", handleResize);
    return () => {
      window.removeEventListener("resize", handleResize);
    };
  }, []);

  return (
    <div className="content flex flex-col items-center justify-center h-screen">
      {/* Conditionally render PreferencesForm based on user login status and window size */}
      {isUser ? (
        <PreferencesForm />
      ) : (
        <div className="flex flex-col items-center">
          <h1 className="text-2xl font-bold">Welcome to the Home Page</h1>
          <p className="mt-4 text-lg">Your content will go here later!</p>
        </div>
      )}
    </div>
  );
};

export default HomePage;
