import React from "react";
import { RadioGroup, Radio, ScrollShadow } from "@nextui-org/react";
import Filter from "./Filters.json";
import ValueSlider from "./Slider";
import AutocompleteBox from "./AutocompleteBox";

const SideFilter = () => {
  const json_filters = Filter.filters;
  console.log(json_filters);
  return (
    <div className="absolute left-4 top-1/2 transform -translate-y-1/2 w-[370px]">
      <ScrollShadow
        hideScrollBar
        offset={100}
        orientation="vertical"
        className="h-[80vh] overflow-y-scroll"
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
                >
                  {filter.buttons.map((button) => (
                    <Radio key={button.value} value={button.value}>
                      {button.innerText}
                    </Radio>
                  ))}
                </RadioGroup>
              );
            } else if (filter.type === "slider") {
              return <ValueSlider filter={filter} key={filter.label} />;
            } else if (filter.type === "autocomplete") {
              return <AutocompleteBox filter={filter} key={filter.label} />;
            }
          })}
        </div>
      </ScrollShadow>
    </div>
  );
};

export default SideFilter;
