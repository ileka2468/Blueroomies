import { Select, SelectItem } from "@nextui-org/react";
import React, { useState } from "react";

const AutocompleteBox = ({ filter }) => {
  const [selectedItem, setSelectedItem] = useState(filter.selectedKey);

  return (
    <div className="flex flex-col space-y-4 mb-4">
      <p className="text-left text-foreground-500 text-base mb-1">
        {filter.label}
      </p>
      <Select
        size={filter.size}
        selectedKeys={selectedItem}
        isRequired
        onSelectionChange={(newItem) => setSelectedItem(newItem)}
      >
        {filter.options.map((item) => (
          <SelectItem key={item.key}>{item.label}</SelectItem>
        ))}
      </Select>
    </div>
  );
};

export default AutocompleteBox;
