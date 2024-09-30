import { Select, SelectItem } from "@nextui-org/react";
import React, { useState } from "react";

const AutocompleteBox = ({ filter, value, onChange }) => {
  let currentValue;

  return (
    <div className="flex flex-col space-y-4 mb-4">
      <p className="text-left text-foreground-500 text-base mb-1">
        {filter.label}
      </p>
      <Select
        size={filter.size}
        selectedKeys={
          value !== undefined ? [value.toString()] : [filter.selectedKey]
        }
        aria-label={filter.label}
        isRequired={true}
        onSelectionChange={(event) => {
          const selectedKey = event.currentKey;
          if (selectedKey) {
            currentValue = selectedKey;
            onChange(selectedKey);
          }
        }}
      >
        {filter.options.map((item) => (
          <SelectItem aria-label={item.label} key={item.key}>
            {item.label}
          </SelectItem>
        ))}
      </Select>
    </div>
  );
};

export default AutocompleteBox;
