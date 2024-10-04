import React from "react";
import { RadioGroup, Radio, ScrollShadow } from "@nextui-org/react";
import Filter from "./Filters.json";
import ValueSlider from "./Slider";
import AutocompleteBox from "./AutocompleteBox";
import { useEffect } from "react";

const SideFilter = ({ filterValues, setFilterValues, userCharacteristics }) => {
  const json_filters = Filter.filters;
  // Initialize filterValues with default values or user's characteristics

  useEffect(() => {
    const initialFilterValues = {};
    json_filters.forEach((filter) => {
      const characteristicKey = mapLabelToCharacteristicKey(filter.label);
      if (
        userCharacteristics &&
        userCharacteristics[characteristicKey] !== undefined
      ) {
        initialFilterValues[filter.label] = Number(
          userCharacteristics[characteristicKey]
        );
      } else {
        initialFilterValues[filter.label] =
          Number(filter.selectedKey) || Number(filter.defaultValue);
      }
    });

    setFilterValues(initialFilterValues);
  }, []);

  // map filter label to characteristic key
  const mapLabelToCharacteristicKey = (label) => {
    const mapping = {
      "Gender Preference": "gender_preference",
      "Smoking Preference": "smoking_preference",
      "Alcohol Usage": "alcohol_usage",
      "Cleanliness Level": "cleanliness_level",
      "Noise Tolerance": "noise_tolerance",
      Hygiene: "hygiene",
      "Sleep Schedule": "sleep_schedule",
      "Guests/Visitors": "guests_visitors",
      "Work/Study From Home": "work_study_from_home",
      "Pet Tolerance": "pet_tolerance",
      "Shared Expenses": "shared_expenses",
      "Study Habits": "study_habits",
      "Room Privacy": "room_privacy",
      "Cooking Frequency": "cooking_frequency",
      "Food Sharing": "food_sharing",
      "Exercise Frequency": "exercise_frequency",
      "Personality Type": "personality_type",
      "Shared Living Space Use": "shared_living_space_use",
      "Room Temperature Preference": "room_temperature_preference",
      "Decorating Style": "decorating_style",
    };
    return mapping[label];
  };

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
          <p className="text-xl font-semibold text-center">Filters</p>
          {json_filters.map((filter) => {
            if (filter.type === "radiogroup") {
              return (
                <RadioGroup
                  label={filter.label}
                  color={filter.color}
                  defaultValue={filter.defaultValue}
                  size={filter.size}
                  key={filter.label}
                  className="mb-6"
                  onValueChange={(value) =>
                    handleFilterChange(filter.label, value)
                  }
                >
                  {filter.buttons.map((button) => (
                    <Radio key={button.value} value={button.value}>
                      {button.innerText}
                    </Radio>
                  ))}
                </RadioGroup>
              );
            } else if (filter.type === "slider") {
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
            } else {
              return null;
            }
          })}
        </div>
      </ScrollShadow>
    </div>
  );
};

export default SideFilter;
