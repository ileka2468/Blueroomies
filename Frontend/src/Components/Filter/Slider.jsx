import { Slider } from "@nextui-org/react";

export default function ValueSlider({ filter, onChange, value }) {
  // Ensure that sliderValue is always a valid number
  let sliderValue = Number(value);

  if (!Number.isFinite(sliderValue)) {
    sliderValue = filter.defaultValue;
  }

  return (
    <div className="flex flex-col space-y-4">
      <p className="text-left text-foreground-500 text-base mb-1">
        {filter.label}
      </p>

      <div className="w-full max-w-md">
        <div className="flex justify-between mb-2">
          <span className="text-tiny font-bold">{filter.leftlabel}</span>
          <span className="text-tiny font-bold">{filter.rightlabel}</span>
        </div>

        <Slider
          aria-label={filter.label}
          size={filter.size}
          step={filter.step}
          minValue={filter.min}
          maxValue={filter.max}
          defaultValue={filter.defaultValue}
          showTooltip={filter.showTooltip}
          showOutline={filter.showOutline}
          onChange={(newValue) => onChange(newValue)}
          value={sliderValue}
          marks={filter.marks.map((mark) => ({
            value: mark.value,
            label: <span className="text-xs">{mark.label}</span>,
          }))}
          className="w-full mb-12"
        />
      </div>
    </div>
  );
}
