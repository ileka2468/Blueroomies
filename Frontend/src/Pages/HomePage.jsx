import React from "react";
import { useOutletContext } from "react-router-dom";
import PreferencesForm from "../Components/Preferences/PreferenceForm.jsx"; // Make sure the path is correct

const HomePage = () => {
  const { isUser } = useOutletContext();

  return (
    <div className="flex flex-col items-center justify-center" style={{ paddingTop: '5px' }}> {/* Adjust the value based on your navbar height */}
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