import React, { useState } from "react";
import { Input, Button, Slider } from "@nextui-org/react";
import axios from "axios";
import useUser from "../../Security/hooks/useUser.js";
import apiClient from "../../Security/axios/apiClient.js";
import SliderPref from "./SliderPref.jsx";

const PreferencesForm = () => {
  const [userData] = useUser();
  const [preferences, setPreferences] = useState({
    genderPreference: "",
    smokingPreference: "",
    alcoholUsage: "",
    cleanlinessLevel: 3,
    noiseTolerance: 3,
    hygiene: 3,
    sleepSchedule: "",
    guestsVisitors: 3,
    workStudyFromHome: 3,
    petTolerance: 3,
    sharedExpenses: 3,
    studyHabits: 3,
    roomPrivacy: 3,
    cookingFrequency: 3,
    foodSharing: "",
    exerciseFrequency: 3,
    personalityType: "",
    sharedLivingSpaceUse: 3,
    roomTemperaturePreference: 3,
    decoratingStyle: "",
  });

  const [buttonText, setButtonText] = useState("Submit Preferences");
  const [message, setMessage] = useState("");

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setPreferences((prevState) => ({
      ...prevState,
      [name]: type === "checkbox" ? checked : value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setMessage("");

    const token = localStorage.getItem("accessToken");
    const response = await apiClient
      .put(
        `${import.meta.env.VITE_API_URL}/preferences/${userData.id}`, // TODO this url wont work
        preferences
      )
      .then((response) => {
        console.log("Access Token before submitting:", token);
        console.log("Preferences updated", response);
        setButtonText("Preferences Submitted Successfully");
      })
      .catch((error) => {
        console.error("Error saving preferences:", error);
      });
  };

  return (
    <form
      onSubmit={handleSubmit}
      style={{
        display: "flex",
        flexDirection: "column",
        gap: "10px",
        width: "100%",
        maxWidth: "600px",
        backgroundColor: "#fff",
        padding: "20px",
        borderRadius: "8px",
        boxShadow: "0 4px 8px rgba(0, 0, 0, 0.1)",
      }}
    >
      {message && (
        <div style={{ marginBottom: "10px", color: "red" }}>{message}</div>
      )}

      <label htmlFor="genderPreference">Gender Preference:</label>
      <select
        name="genderPreference"
        id="genderPreference"
        value={preferences.genderPreference}
        onChange={handleChange}
        style={{ padding: "8px", borderRadius: "4px" }}
      >
        <option value="">Select Gender</option>
        <option value="male">Male</option>
        <option value="female">Female</option>
        <option value="nonbinary">Nonbinary</option>
        <option value="no-preference">No Preference</option>
      </select>

      <label htmlFor="smokingPreference">Smoking Preference:</label>
      <select
        name="smokingPreference"
        id="smokingPreference"
        value={preferences.smokingPreference}
        onChange={handleChange}
        style={{ padding: "8px", borderRadius: "4px" }}
      >
        <option value="">Select Smoking Preference</option>
        <option value="smoking">Smoking</option>
        <option value="no-smoking">No Smoking</option>
        <option value="no-preference">No Preference</option>
      </select>

      <label htmlFor="alcoholUsage">Alcohol Usage Preference:</label>
      <select
        name="alcoholUsage"
        id="alcoholUsage"
        value={preferences.alcoholUsage}
        onChange={handleChange}
        style={{ padding: "8px", borderRadius: "4px" }}
      >
        <option value="">Select Alcohol Preference</option>
        <option value="alcohol">Drinks Alcohol</option>
        <option value="no-alcohol">No Alcohol</option>
        <option value="no-preference">No Preference</option>
      </select>

      <SliderPref
        name="cleanlinessLevel"
        value={preferences.cleanlinessLevel}
        onChange={(value) =>
          setPreferences((prev) => ({ ...prev, cleanlinessLevel: value }))
        }
        label="Cleanliness Level"
      />
      <SliderPref
        name="noiseTolerance"
        value={preferences.noiseTolerance}
        onChange={(value) =>
          setPreferences((prev) => ({ ...prev, noiseTolerance: value }))
        }
        label="Noise Tolerance"
      />
      <SliderPref
        name="hygiene"
        value={preferences.hygiene}
        onChange={(value) =>
          setPreferences((prev) => ({ ...prev, hygiene: value }))
        }
        label="Hygiene Level"
      />
      <label htmlFor="sleepSchedule">Sleep Schedule Preference:</label>
      <select
        name="sleepSchedule"
        id="sleepSchedule"
        value={preferences.sleepSchedule}
        onChange={handleChange}
        style={{ padding: "8px", borderRadius: "4px" }}
      >
        <option value="">Select Sleep Schedule Preference</option>
        <option value="early-riser">Early Riser</option>
        <option value="night-owl">Night Owl</option>
        <option value="flexible">Flexible</option>
      </select>
      <SliderPref
        name="guestsVisitors"
        value={preferences.guestsVisitors}
        min={1}
        max={5}
        onChange={(value) =>
          setPreferences((prev) => ({ ...prev, guestsVisitors: value }))
        }
        label="Guests/Visitors Tolerance"
      />
      <SliderPref
        name="workStudyFromHome"
        value={preferences.workStudyFromHome}
        min={1}
        max={5}
        onChange={(value) =>
          setPreferences((prev) => ({ ...prev, workStudyFromHome: value }))
        }
        label="Work/Study from Home Preference"
      />
      <SliderPref
        name="petTolerance"
        value={preferences.petTolerance}
        onChange={(value) =>
          setPreferences((prev) => ({ ...prev, petTolerance: value }))
        }
        label="Pet Tolerance"
      />
      <SliderPref
        name="sharedExpenses"
        value={preferences.sharedExpenses}
        onChange={(value) =>
          setPreferences((prev) => ({ ...prev, sharedExpenses: value }))
        }
        label="Shared Expenses Preference"
      />
      <SliderPref
        name="studyHabits"
        value={preferences.studyHabits}
        onChange={(value) =>
          setPreferences((prev) => ({ ...prev, studyHabits: value }))
        }
        label="Study Habits"
      />
      <SliderPref
        name="roomPrivacy"
        value={preferences.roomPrivacy}
        onChange={(value) =>
          setPreferences((prev) => ({ ...prev, roomPrivacy: value }))
        }
        label="Room Privacy Preference"
      />
      <SliderPref
        name="cookingFrequency"
        value={preferences.cookingFrequency}
        onChange={(value) =>
          setPreferences((prev) => ({ ...prev, cookingFrequency: value }))
        }
        label="Cooking Frequency"
      />
      <label htmlFor="foodSharing">Food Sharing Preference:</label>
      <select
        name="foodSharing"
        id="foodSharing"
        value={preferences.foodSharing}
        onChange={handleChange}
        style={{ padding: "8px", borderRadius: "4px" }}
      >
        <option value="">Select Food Sharing Preference</option>
        <option value="yes">True</option>
        <option value="no">False</option>
        <option value="no-preference">No Preference</option>
      </select>
      <SliderPref
        name="exerciseFrequency"
        value={preferences.exerciseFrequency}
        onChange={(value) =>
          setPreferences((prev) => ({ ...prev, exerciseFrequency: value }))
        }
        label="Exercise Frequency"
      />
      <label htmlFor="personalityType">Your Personality Type:</label>
      <select
        name="personalityType"
        id="personalityType"
        value={preferences.personalityType}
        onChange={handleChange}
        style={{ padding: "8px", borderRadius: "4px" }}
      >
        <option value="">Select Your Personality Type</option>
        <option value="introverted">Introverted</option>
        <option value="extroverted">Extroverted</option>
        <option value="ambivert">Ambivert</option>
      </select>
      <SliderPref
        name="sharedLivingSpaceUse"
        value={preferences.sharedLivingSpaceUse}
        onChange={(value) =>
          setPreferences((prev) => ({ ...prev, sharedLivingSpaceUse: value }))
        }
        label="Shared Living Space Use"
      />
      <SliderPref
        name="roomTemperaturePreference"
        value={preferences.roomTemperaturePreference}
        onChange={(value) =>
          setPreferences((prev) => ({
            ...prev,
            roomTemperaturePreference: value,
          }))
        }
        label="Room Temperature Preference"
      />
      <label htmlFor="decoratingStyle">Decorating Style Preference:</label>
      <select
        name="decoratingStyle"
        id="decoratingStyle"
        value={preferences.decoratingStyle}
        onChange={handleChange}
        style={{ padding: "8px", borderRadius: "4px" }}
      >
        <option value="">Select Decorating Style</option>
        <option value="minimalist">Minimalist</option>
        <option value="cozy">Cozy</option>
        <option value="modern">Modern</option>
        <option value="traditional">Traditional</option>
      </select>

      <Button type="submit" style={{ marginTop: "20px" }}>
        {buttonText}
      </Button>
    </form>
  );
};

export default PreferencesForm;
