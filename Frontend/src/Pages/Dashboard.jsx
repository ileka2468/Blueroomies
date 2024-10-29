import React from 'react';
import PreferencesForm from '../Components/Preferences/PreferenceForm.jsx'; // Ensure you import the PreferencesForm

const Dashboard = () => {
  return (
    <div className="pt-0.5"> {/* Add padding-top to ensure it’s below the nav */}
      {/* Blue Background for the Heading */}
      <h3 className="text-3xl text-center bg-blue-500 text-white p-4 rounded-md mb-6" >
        Dashboard
      </h3>
      <p className="text-lg text-center"> New Users: Please Fill Out This Preference Form </p>
      <div className="flex justify-center"> {/* Center the PreferencesForm */}
        <PreferencesForm /> {/* Display the PreferencesForm for authenticated users */}
      </div>
    </div>
  );
};

export default Dashboard;
