import { useState, useMemo } from "react";
import { useAxios } from "./Security/axios/AxiosProvider";
import Nav from "./Components/Dashboard/Nav/Nav";
import { useDisclosure } from "@nextui-org/react";
import LoginModal from "./Components/Authentication/LoginModal";
import RegisterModal from "./Components/Authentication/RegisterModal";
import useUser from "./Security/hooks/useUser";
import SideFilter from "./Components/Filter/SideFilter";
import RoommateResults from "./Components/RoommateResults";
import Filters from "./Components/Filter/Filters.json";

function App() {
  const apiClient = useAxios();
  const [userData, setUserData, isUser] = useUser();
  const [matches, setMatches] = useState([]);
  const [filterValues, setFilterValues] = useState(Filters.filters);

  console.log(filterValues);

  // Fetch matches from backend

  const getMatches = async () => {
    try {
      const response = await apiClient.post("/matches/run");
      setMatches(response.data.matches);
    } catch (e) {
      console.log(e);
    }
  };

  // apply filters to matches
  const applyFilters = (matches, filters) => {
    return matches.filter((match) => {
      const { profile } = match;

      // Gender Preference
      if (
        filters["Gender Preference"] &&
        filters["Gender Preference"] !== "2"
      ) {
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

      // Alcohol Usage
      if (filters["Alcohol Usage"] !== undefined) {
        if (profile.alcohol_usage !== parseInt(filters["Alcohol Usage"])) {
          return false;
        }
      }

      // Cleanliness Level
      if (filters["Cleanliness Level"] !== undefined) {
        if (
          profile.cleanliness_level < parseInt(filters["Cleanliness Level"])
        ) {
          return false;
        }
      }

      // Noise Tolerance
      if (filters["Noise Tolerance"] !== undefined) {
        if (profile.noise_tolerance < parseInt(filters["Noise Tolerance"])) {
          return false;
        }
      }

      // Hygiene
      if (filters["Hygiene"] !== undefined) {
        if (profile.hygiene < parseInt(filters["Hygiene"])) {
          return false;
        }
      }

      // Sleep Schedule
      if (filters["Sleep Schedule"] !== undefined) {
        if (profile.sleep_schedule !== parseInt(filters["Sleep Schedule"])) {
          return false;
        }
      }

      // Guests/Visitors
      if (filters["Guests/Visitors"] !== undefined) {
        if (profile.guests_visitors < parseInt(filters["Guests/Visitors"])) {
          return false;
        }
      }

      // Work/Study From Home
      if (filters["Work/Study From Home"] !== undefined) {
        if (
          profile.work_study_from_home <
          parseInt(filters["Work/Study From Home"])
        ) {
          return false;
        }
      }

      // Pet Tolerance
      if (filters["Pet Tolerance"] !== undefined) {
        if (profile.pet_tolerance < parseInt(filters["Pet Tolerance"])) {
          return false;
        }
      }

      // Shared Expenses
      if (filters["Shared Expenses"] !== undefined) {
        if (profile.shared_expenses < parseInt(filters["Shared Expenses"])) {
          return false;
        }
      }

      // Study Habits
      if (filters["Study Habits"] !== undefined) {
        if (profile.study_habits < parseInt(filters["Study Habits"])) {
          return false;
        }
      }

      // Room Privacy
      if (filters["Room Privacy"] !== undefined) {
        if (profile.room_privacy < parseInt(filters["Room Privacy"])) {
          return false;
        }
      }

      // Cooking Frequency
      if (filters["Cooking Frequency"] !== undefined) {
        if (
          profile.cooking_frequency < parseInt(filters["Cooking Frequency"])
        ) {
          return false;
        }
      }

      // Food Sharing - not as Strict
      if (filters["Food Sharing"] !== undefined) {
        const userPreference = parseInt(filters["Food Sharing"]);
        const matchPreference = profile.food_sharing;
        // Allow matches if either user or match is willing to share food
        if (userPreference === 1 && matchPreference === 1) {
          // Both want to share food do nothing
        } else if (userPreference === 0 && matchPreference === 0) {
          // Both prefer not to share food do nothing
        } else {
          // Preferences differ allow thorugh tho, but possibly notify user on user card.
        }
      }

      // Exercise Frequency
      if (filters["Exercise Frequency"] !== undefined) {
        if (
          profile.exercise_frequency < parseInt(filters["Exercise Frequency"])
        ) {
          return false;
        }
      }

      // Personality Type +-1 loose
      if (filters["Personality Type"] !== undefined) {
        if (
          Math.abs(
            profile.personality_type - parseInt(filters["Personality Type"])
          ) > 1
        ) {
          return false;
        }
      }

      // Shared Living Space Use
      if (filters["Shared Living Space Use"] !== undefined) {
        if (
          profile.shared_living_space_use <
          parseInt(filters["Shared Living Space Use"])
        ) {
          return false;
        }
      }

      // Room Temperature Preference +-1 loose
      if (filters["Room Temperature Preference"] !== undefined) {
        if (
          Math.abs(
            profile.room_temperature_preference -
              parseInt(filters["Room Temperature Preference"])
          ) > 1
        ) {
          return false;
        }
      }

      // decorating Style - Less Strict
      if (filters["Decorating Style"] !== undefined) {
        if (
          profile.decorating_style === parseInt(filters["Decorating Style"])
        ) {
          // Exact match do nothin
        } else {
          // preferences differ but allow through tho and notify user on match card.
        }
      }

      return true;
    });
  };

  const filteredMatches = useMemo(
    () => applyFilters(matches, filterValues),
    [matches, filterValues]
  );

  // useDisclosure for Modals
  const {
    isOpen: isLoginOpen,
    onOpen: onLoginOpen,
    onOpenChange: onLoginOpenChange,
  } = useDisclosure();

  const {
    isOpen: isRegisterOpen,
    onOpen: onRegisterOpen,
    onOpenChange: onRegisterOpenChange,
  } = useDisclosure();

  return (
    <main className="h-full px-4 pt-16">
      {/* Login Modal */}
      <LoginModal
        isOpen={isLoginOpen}
        onOpen={onLoginOpen}
        onOpenChange={onLoginOpenChange}
        setUserData={setUserData}
        isUser={isUser}
      />
      {/* Register Modal */}
      <RegisterModal
        isOpen={isRegisterOpen}
        onOpen={onRegisterOpen}
        onOpenChange={onRegisterOpenChange}
        setUserData={setUserData}
      />
      <Nav
        onRegisterOpen={onRegisterOpen}
        onLoginOpen={onLoginOpen}
        userData={userData}
        setUserData={setUserData}
        isUser={isUser}
      />
      <div className="flex h-[90vh]">
        {/* Side Filter */}
        <SideFilter
          filterValues={filterValues}
          setFilterValues={setFilterValues}
          userCharacteristics={userData?.characteristics}
        />
        {/* Roommate Results */}
        <div className="flex-grow px-4">
          <h2 className="text-lg font-bold">Roommate Results</h2>
          <RoommateResults matches={filteredMatches} />
        </div>
      </div>
    </main>
  );
}

export default App;
