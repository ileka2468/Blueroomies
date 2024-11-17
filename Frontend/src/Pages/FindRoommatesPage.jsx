import React, { useState } from "react";
import { useAxios } from "../Security/axios/AxiosProvider";
import SideFilter from "../Components/Filter/SideFilter";
import RoommateResults from "../Components/RoommateResults";
import { Button, Switch } from "@nextui-org/react";

const FindRoommatesPage = () => {
  const apiClient = useAxios();
  const [matches, setMatches] = useState([]);
  const [filterValues, setFilterValues] = useState({});
  const [isLoading, setIsLoading] = useState(false);
  const [showRawData, setShowRawData] = useState(false);
  const [filteringEnabled, setFilteringEnabled] = useState(true);

  const getMatches = async () => {
    try {
      setIsLoading(true);
      const response = await apiClient.post("/matches/run");

      const data =
        typeof response.data === "string"
          ? JSON.parse(response.data)
          : response.data;

      console.log("Raw matches data:", data);

      if (data.matches) {
        setMatches(data.matches);
      }
    } catch (error) {
      console.error("Error fetching matches:", error);
    } finally {
      setIsLoading(false);
    }
  };

  const applyFilters = (matches, filters) => {
    if (!filteringEnabled) return matches;

    return matches.filter((match) => {
      const characteristics = match.profile;

      // Helper function to check numeric tolerance
      const isWithinTolerance = (value, target, tolerance = 1) => {
        return Math.abs(value - target) <= tolerance;
      };

      // Only filter numeric characteristics with tolerance
      const numericFilters = {
        "Noise Tolerance": "noise_tolerance",
        "Guests/Visitors": "guests_visitors",
        "Work/Study From Home": "work_study_from_home",
        "Pet Tolerance": "pet_tolerance",
        "Shared Expenses": "shared_expenses",
        "Study Habits": "study_habits",
        "Room Privacy": "room_privacy",
        "Cooking Frequency": "cooking_frequency",
        "Exercise Frequency": "exercise_frequency",
        "Shared Living Space Use": "shared_living_space_use",
        "Room Temperature Preference": "room_temperature_preference",
      };

      for (const [filterName, characteristicKey] of Object.entries(
        numericFilters
      )) {
        if (filters[filterName] !== undefined) {
          const filterValue = Number(filters[filterName]);
          const characteristicValue = Number(
            characteristics[characteristicKey]
          );
          if (!isWithinTolerance(characteristicValue, filterValue)) {
            return false;
          }
        }
      }

      return true;
    });
  };

  const filteredMatches = applyFilters(matches, filterValues);

  return (
    <div className="flex h-[calc(100vh-4rem)] overflow-y-auto">
      <div className="sticky top-0 h-full">
        <SideFilter
          filterValues={filterValues}
          setFilterValues={setFilterValues}
          userCharacteristics={{}}
        />
      </div>
      <div className="flex-grow px-4">
        <div className="mb-6 flex items-center gap-4">
          <Button color="primary" onClick={getMatches} isLoading={isLoading}>
            Find Matches
          </Button>
          {import.meta.env.VITE_NODE_ENV == "dev" && (
            <Switch isSelected={showRawData} onValueChange={setShowRawData}>
              Show Raw Data
            </Switch>
          )}

          <Switch
            isSelected={filteringEnabled}
            onValueChange={setFilteringEnabled}
          >
            Enable Filtering
          </Switch>
        </div>

        {showRawData && matches.length > 0 && (
          <div className="mb-6">
            <h3 className="text-lg font-semibold mb-2">Raw Match Data:</h3>
            <pre className="bg-gray-100 p-4 rounded overflow-auto max-h-[300px]">
              {JSON.stringify(matches, null, 2)}
            </pre>
          </div>
        )}

        <RoommateResults
          matches={filteredMatches}
          isLoading={isLoading}
          onRefresh={getMatches}
        />
      </div>
    </div>
  );
};

export default FindRoommatesPage;
