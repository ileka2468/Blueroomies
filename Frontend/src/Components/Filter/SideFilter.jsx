import React from "react";
import { ScrollShadow } from "@nextui-org/react";
import Filter from "./Filters.json";
import ValueSlider from "./Slider";
import AutocompleteBox from "./AutocompleteBox";

const SideFilter = ({ filterValues, setFilterValues, userCharacteristics }) => {
  // Only include filters that aren't pre-filtered on the backend
  const relevantFilters = [
    "Noise Tolerance",
    "Guests/Visitors",
    "Work/Study From Home",
    "Pet Tolerance",
    "Shared Expenses",
    "Study Habits",
    "Room Privacy",
    "Cooking Frequency",
    "Exercise Frequency",
    "Shared Living Space Use",
    "Room Temperature Preference",
  ];

  const json_filters = Filter.filters.filter((filter) =>
    relevantFilters.includes(filter.label)
  );

  const handleFilterChange = (label, value) => {
    setFilterValues((prevValues) => ({
      ...prevValues,
      [label]: Number(value),
    }));
  };

  return (
    <div className="w-[370px] h-full">
      <ScrollShadow
        hideScrollBar
        offset={100}
        orientation="vertical"
        className="h-[100%] overflow-y-scroll"
      >
        <div className="border-filtergrey border-solid border-1 rounded-xl w-full p-8">
          <p className="text-xl font-semibold text-center mb-6">Preferences</p>
          {json_filters.map((filter) => {
            if (filter.type === "slider") {
              return (
                <ValueSlider
                  filter={filter}
                  key={filter.label}
                  onChange={(value) => handleFilterChange(filter.label, value)}
                  value={
                    filterValues[filter.label] !== undefined
                      ? filterValues[filter.label]
                      : filter.defaultValue
                  }
                />
              );
            } else if (filter.type === "autocomplete") {
              return (
                <AutocompleteBox
                  filter={filter}
                  key={filter.label}
                  value={filterValues[filter.label]}
                  onChange={(value) => handleFilterChange(filter.label, value)}
                />
              );
            }
            return null;
          })}
        </div>
      </ScrollShadow>
    </div>
  );
};

export default SideFilter;
