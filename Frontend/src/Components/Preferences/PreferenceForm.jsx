import React, { useState } from "react";
import { Input, Button, Slider } from "@nextui-org/react";
import axios from "axios";
import useUser from "../../Security/hooks/useUser.js";
import apiClient from "../../Security/axios/apiClient.js";
import SliderPref from "./SliderPref.jsx";

const PreferencesForm = () => {
  const [userData] = useUser();
  console.log(userData);
  const [preferences, setPreferences] = useState({
    gender_preference: "No Preference",
    smoking_preference: false,
    alcohol_usage: false,
    cleanliness_level: 3,
    noise_tolerance: 3,
    hygiene: 3,
    sleep_schedule: "Flexible",
    guests_visitors: 3,
    work_study_from_home: 3,
    pet_tolerance: 3,
    shared_expenses: 3,
    study_habits: 3,
    room_privacy: 3,
    cooking_frequency: 3,
    food_sharing: false,
    exercise_frequency: 3,
    personality_type: "Ambivert",
    shared_living_space_use: 3,
    room_temperature_preference: 3,
    decorating_style: "Modern",
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
    const env = import.meta.env.VITE_NODE_ENV;

    const response = await apiClient.put(
      "/preferences/save", // TODO this url wont work
      preferences
    );
    console.log(response);
    setButtonText('Preferences Submitted Successfully');
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

      <label htmlFor="gender_preference">Gender Preference:</label>
      <select
        name="gender_preference"
        id="gender_preference"
        value={preferences.gender_preference}
        onChange={handleChange}
        style={{ padding: "8px", borderRadius: "4px" }}
      >
        <option value="">Select Gender</option>
        <option value="male">Male</option>
        <option value="Female">Female</option>
        <option value="Nonbinary">Nonbinary</option>
        <option value="No Preference">No Preference</option>
      </select>

      <label htmlFor="smoking_preference">Smoking Preference:</label>
      <select
          name="smoking_preference"
          id="smoking_preference"
          value={preferences.smoking_preference ? "true" : "false"}
          onChange={(e) => {
            const { value } = e.target;
              setPreferences((prev) => ({
              ...prev,
            smoking_preference: value === "true" ? true : value === "false" ? false : null,
            }));
          }}
        style={{ padding: "8px", borderRadius: "4px" }}
      >
        <option value="">Select Smoking Preference</option>
        <option value="true">Smoking</option>
        <option value="false">No Smoking</option>
        <option value="no-preference">No Preference</option>
      </select>

      <label htmlFor="alcohol_usage">Alcohol Usage Preference:</label>
      <select
        name="alcohol_usage"
        id="alcohol_usage"
        value={preferences.alcohol_usage ? "true" : "false"}
        onChange={(e) => {
          const { value } = e.target;
          setPreferences((prev) => ({
            ...prev,
            alcohol_usage: value === "true" ? true : value === "false" ? false : null,
          }));
        }}
        style={{ padding: "8px", borderRadius: "4px" }}
      >
        <option value="">Select Alcohol Preference</option>
        <option value="true">Drinks Alcohol</option>
        <option value="false">No Alcohol</option>
        <option value="no-preference">No Preference</option>
      </select>

      <SliderPref
        name="cleanliness_level"
        id="cleanliness_level"
        value={preferences.cleanliness_level}
        onChange={(value) =>
          setPreferences((prev) => ({ ...prev, cleanliness_level: value }))
        }
        label="Cleanliness Level"
      />
      <SliderPref
        name="noise_tolerance"
        id="noise_tolerance"
        value={preferences.noise_tolerance}
        onChange={(value) =>
          setPreferences((prev) => ({ ...prev, noise_tolerance: value }))
        }
        label="Noise Tolerance"
      />
      <SliderPref
        name="hygiene"
        id="hygiene"
        value={preferences.hygiene}
        onChange={(value) =>
          setPreferences((prev) => ({ ...prev, hygiene: value }))
        }
        label="Hygiene Level"
      />
      <label htmlFor="sleep_schedule">Sleep Schedule Preference:</label>
      <select
        name="sleep_schedule"
        id="sleep_schedule"
        value={preferences.sleep_schedule}
        onChange={handleChange}
        style={{ padding: "8px", borderRadius: "4px" }}
      >
        <option value="">Select Sleep Schedule Preference</option>
        <option value="Early Riser">Early Riser</option>
        <option value="Night Owl">Night Owl</option>
        <option value="Flexible">Flexible</option>
      </select>
      <SliderPref
        name="guests_visitors"
        id="guests_visitors"
        value={preferences.guests_visitors}
        min={1}
        max={5}
        onChange={(value) =>
          setPreferences((prev) => ({ ...prev, guests_visitors: value }))
        }
        label="Guests/Visitors Tolerance"
      />
      <SliderPref
        name="work_study_from_home"
        id="work_study_from_home"
        value={preferences.work_study_from_home}
        min={1}
        max={5}
        onChange={(value) =>
          setPreferences((prev) => ({ ...prev, work_study_from_home: value }))
        }
        label="Work/Study from Home Preference"
      />
      <SliderPref
        name="pet_tolerance"
        id="pet_tolerance"
        value={preferences.pet_tolerance}
        onChange={(value) =>
          setPreferences((prev) => ({ ...prev, pet_tolerance: value }))
        }
        label="Pet Tolerance"
      />
      <SliderPref
        name="shared_expenses"
        id="shared_expenses"
        value={preferences.shared_expenses}
        onChange={(value) =>
          setPreferences((prev) => ({ ...prev, shared_expenses: value }))
        }
        label="Shared Expenses Preference"
      />
      <SliderPref
        name="study_habits"
        id="study_habits"
        value={preferences.study_habits}
        onChange={(value) =>
          setPreferences((prev) => ({ ...prev, study_habits: value }))
        }
        label="Study Habits"
      />
      <SliderPref
        name="room_privacy"
        id="room_privacy"
        value={preferences.room_privacy}
        onChange={(value) =>
          setPreferences((prev) => ({ ...prev, room_privacy: value }))
        }
        label="Room Privacy Preference"
      />
      <SliderPref
        name="cooking_frequency"
        id="cooking_frequency"
        value={preferences.cooking_frequency}
        onChange={(value) =>
          setPreferences((prev) => ({ ...prev, cooking_frequency: value }))
        }
        label="Cooking Frequency"
      />
      <label htmlFor="foodSharing">Food Sharing Preference:</label>
      <select
         name="food_sharing"
         id="food_sharing"
         value={preferences.food_sharing ? "true" : "false"}
         onChange={(e) => {
           const { value } = e.target;
           setPreferences((prev) => ({
             ...prev,
             food_sharing: value === "true" ? true : value === "false" ? false : null,
           }));
         }}
         style={{ padding: "8px", borderRadius: "4px" }}
      >
        <option value="">Select Food Sharing Preference</option>
        <option value="true">Ok with Sharing</option>
        <option value="false">No Sharing</option>
        <option value="No Preference">No Preference</option>
      </select>
      <SliderPref
        name="exercise_frequency"
        id="exercise_frequency"
        value={preferences.exercise_frequency}
        onChange={(value) =>
          setPreferences((prev) => ({ ...prev, exercise_frequency: value }))
        }
        label="Exercise Frequency"
      />
      <label htmlFor="personalityType">Your Personality Type:</label>
      <select
        name="personality_type"
        id="personality_type"
        value={preferences.personality_type}
        onChange={handleChange}
        style={{ padding: "8px", borderRadius: "4px" }}
      >
        <option value="">Select Your Personality Type</option>
        <option value="Introverted">Introverted</option>
        <option value="Extroverted">Extroverted</option>
        <option value="Ambivert">Ambivert</option>
      </select>
      <SliderPref
        name="shared_living_space_use"
        id="shared_living_space_use"
        value={preferences.shared_living_space_use}
        onChange={(value) =>
          setPreferences((prev) => ({ ...prev, shared_living_space_use: value }))
        }
        label="Shared Living Space Use"
      />
      <SliderPref
        name="room_temperature_preference"
        id="room_temperature_preference"
        value={preferences.room_temperature_preference}
        onChange={(value) =>
          setPreferences((prev) => ({
            ...prev,
            room_temperature_preference: value,
          }))
        }
        label="Room Temperature Preference"
      />
      <label htmlFor="decorating_style">Decorating Style Preference:</label>
      <select
        name="decorating_style"
        id="decorating_style"
        value={preferences.decorating_style}
        onChange={handleChange}
        style={{ padding: "8px", borderRadius: "4px" }}
      >
        <option value="">Select Decorating Style</option>
        <option value="Minimalist">Minimalist</option>
        <option value="Cozy">Cozy</option>
        <option value="Modern">Modern</option>
        <option value="Traditional">Traditional</option>
      </select>

      <Button type="submit" style={{ marginTop: "20px" }}>
        {buttonText}
      </Button>
    </form>
  );
};

export default PreferencesForm;
