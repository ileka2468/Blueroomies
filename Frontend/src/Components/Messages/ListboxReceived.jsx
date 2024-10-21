import { extendVariants, ListboxItem } from "@nextui-org/react";

export const ListboxReceived = extendVariants(ListboxItem, {
    variants: {
      color: {
        gray: "text-[#FFFFFF] bg-[#AAAAAA]",
      },
      size: {
        md: "px-4 min-w-20 h-10 text-small gap-2 rounded-medium h-auto w-auto",
      },
    },
    defaultVariants: {
      color: "gray",
      size: "md",
    },
  });