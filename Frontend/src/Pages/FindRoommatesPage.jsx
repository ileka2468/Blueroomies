import React from "react";
import SideFilter from "../Components/Filter/SideFilter";
import RoommateResults from "../Components/RoommateResults";
import { useAxios } from "../Security/axios/AxiosProvider";
import { useState } from "react";
import { useMemo } from "react";
import useUser from "../Security/hooks/useUser";

const FindRoommatesPage = () => {
  const [userData, setUserData, isUser] = useUser();
  // const [matches, setMatches] = useState([]);

  const apiClient = useAxios();
  const [filterValues, setFilterValues] = useState({});

  const [matches, setMatches] = useState([
    {
      user_id: 5,
      match_score: 87,
      profile: {
        name: "John Doe",
        bio: "Looking for a quiet roommate.",
        avatarUrl: "https://i.pravatar.cc/150?img=5", // Placeholder image
        budget: 900,
        oncampus: false,
        cleanliness: "High",
        smoking: "Non-smoker",
        pets: "No",
        noise: "Quiet",
        proximity: "Campus",
      },
    },
    {
      user_id: 8,
      match_score: 82,
      profile: {
        name: "Jane Smith",
        bio: "Night owl, loves pets.",
        avatarUrl: "https://i.pravatar.cc/150?img=8", // Placeholder image
        budget: 700,
        oncampus: false,
        cleanliness: "Medium",
        smoking: "Non-smoker",
        pets: "Yes",
        noise: "Moderate",
        proximity: "Off-campus",
      },
    },
  ]);
  // Fetch matches from backend

  const getMatches = async () => {
    try {
      const response = await apiClient.post("/matches/run");
      setMatches(response.data.matches);
    } catch (e) {
      console.log(e);
    }
  };

  // Apply filters to matches
  const applyFilters = (matches, filters) => {
    return matches.filter((match) => {
      const { profile } = match;

      // Gender Preference (Example: Allow both "No Preference" and the same gender)
      if (filters["Gender Preference"] && filters["Gender Preference"] != 2) {
        if (
          profile.gender_preference !== parseInt(filters["Gender Preference"])
        ) {
          return false;
        }
      }

      // Smoking Preference
      if (filters["Smoking Preference"] !== undefined) {
        if (
          profile.smoking_preference !== parseInt(filters["Smoking Preference"])
        ) {
          return false;
        }
      }

      // // Alcohol Usage
      // if (filters["Alcohol Usage"] !== undefined) {
      //   if (profile.alcohol_usage !== parseInt(filters["Alcohol Usage"])) {
      //     return false;
      //   }
      // }

      // Cleanliness Level within 1 level of preference
      if (filters["Cleanliness Level"] !== undefined) {
        if (
          Math.abs(
            profile.cleanliness_level - parseInt(filters["Cleanliness Level"])
          ) > 1
        ) {
          return false;
        }
      }

      // // Noise Tolerance within 1 level of preference
      // if (filters["Noise Tolerance"] !== undefined) {
      //   if (
      //     Math.abs(
      //       profile.noise_tolerance - parseInt(filters["Noise Tolerance"])
      //     ) > 1
      //   ) {
      //     return false;
      //   }
      // }

      // // Hygiene (Allowing a range: within 1 level)
      // if (filters["Hygiene"] !== undefined) {
      //   if (Math.abs(profile.hygiene - parseInt(filters["Hygiene"])) > 1) {
      //     return false;
      //   }
      // }

      // // Sleep Schedule (Exact match required)
      // if (filters["Sleep Schedule"] !== undefined) {
      //   if (profile.sleep_schedule !== parseInt(filters["Sleep Schedule"])) {
      //     return false;
      //   }
      // }

      // // Guests/Visitors (Allowing a range: within 1 level)
      // if (filters["Guests/Visitors"] !== undefined) {
      //   if (
      //     Math.abs(
      //       profile.guests_visitors - parseInt(filters["Guests/Visitors"])
      //     ) > 1
      //   ) {
      //     return false;
      //   }
      // }

      // // Work/Study From Home (Allowing a range)
      // if (filters["Work/Study From Home"] !== undefined) {
      //   if (
      //     Math.abs(
      //       profile.work_study_from_home -
      //         parseInt(filters["Work/Study From Home"])
      //     ) > 1
      //   ) {
      //     return false;
      //   }
      // }

      // // Pet Tolerance (Allowing a range)
      // if (filters["Pet Tolerance"] !== undefined) {
      //   if (
      //     Math.abs(profile.pet_tolerance - parseInt(filters["Pet Tolerance"])) >
      //     1
      //   ) {
      //     return false;
      //   }
      // }

      // // Shared Expenses (Allowing a range)
      // if (filters["Shared Expenses"] !== undefined) {
      //   if (
      //     Math.abs(
      //       profile.shared_expenses - parseInt(filters["Shared Expenses"])
      //     ) > 1
      //   ) {
      //     return false;
      //   }
      // }

      // // Study Habits (Allowing a range)
      // if (filters["Study Habits"] !== undefined) {
      //   if (
      //     Math.abs(profile.study_habits - parseInt(filters["Study Habits"])) > 1
      //   ) {
      //     return false;
      //   }
      // }

      // // Room Privacy (Allowing a range)
      // if (filters["Room Privacy"] !== undefined) {
      //   if (
      //     Math.abs(profile.room_privacy - parseInt(filters["Room Privacy"])) > 1
      //   ) {
      //     return false;
      //   }
      // }

      // // Cooking Frequency (Allowing a range)
      // if (filters["Cooking Frequency"] !== undefined) {
      //   if (
      //     Math.abs(
      //       profile.cooking_frequency - parseInt(filters["Cooking Frequency"])
      //     ) > 1
      //   ) {
      //     return false;
      //   }
      // }

      // // Food Sharing - Not strict
      // if (filters["Food Sharing"] !== undefined) {
      //   const userPreference = parseInt(filters["Food Sharing"]);
      //   const matchPreference = profile.food_sharing;
      //   // Let some differences in food sharing pass, but could notify user on result card.
      // }

      // // Exercise Frequency (Allowing a range)
      // if (filters["Exercise Frequency"] !== undefined) {
      //   if (
      //     Math.abs(
      //       profile.exercise_frequency - parseInt(filters["Exercise Frequency"])
      //     ) > 1
      //   ) {
      //     return false;
      //   }
      // }

      // // Personality Type (Allow +-1 flexibility)
      // if (filters["Personality Type"] !== undefined) {
      //   if (
      //     Math.abs(
      //       profile.personality_type - parseInt(filters["Personality Type"])
      //     ) > 1
      //   ) {
      //     return false;
      //   }
      // }

      // // Shared Living Space Use (Allowing a range)
      // if (filters["Shared Living Space Use"] !== undefined) {
      //   if (
      //     Math.abs(
      //       profile.shared_living_space_use -
      //         parseInt(filters["Shared Living Space Use"])
      //     ) > 1
      //   ) {
      //     return false;
      //   }
      // }

      // // Room Temperature Preference (Allow +-1 flexibility)
      // if (filters["Room Temperature Preference"] !== undefined) {
      //   if (
      //     Math.abs(
      //       profile.room_temperature_preference -
      //         parseInt(filters["Room Temperature Preference"])
      //     ) > 1
      //   ) {
      //     return false;
      //   }
      // }

      // // Decorating Style (Less strict)
      // if (filters["Decorating Style"] !== undefined) {
      //   if (
      //     profile.decorating_style !== parseInt(filters["Decorating Style"])
      //   ) {
      //     // Allow through, just notify in match card
      //   }
      // }

      return true;
    });
  };

  const filteredMatches = useMemo(
    () => applyFilters(matches, filterValues),
    [matches, filterValues]
  );

  return (
    <div className="flex h-[calc(100vh-4rem)] overflow-y-auto">
      {/* Side Filter */}
      <div className="sticky top-0 h-full">
        <SideFilter
          filterValues={filterValues}
          setFilterValues={setFilterValues}
          userCharacteristics={{}}
        />
      </div>
      {/* Roommate Results */}
      <div className="flex-grow px-4">
        <h2 className="text-2xl font-bold mb-4">Roommate Results</h2>
        <RoommateResults matches={matches} />
      </div>
    </div>
  );
};

export default FindRoommatesPage;
