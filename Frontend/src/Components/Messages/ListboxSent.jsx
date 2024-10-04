import { extendVariants, ListboxItem } from "@nextui-org/react";

export const ListboxSent = extendVariants(ListboxItem, {
    variants: {
      color: {
        blue: "text-[#FFFFFF] bg-[#99C7FB]"
      },
      size: {
        md: "px-4 min-w-20 h-10 text-small gap-2 rounded-medium h-auto w-auto",
      },
    },
    defaultVariants: {
      color: "blue",
      size: "md",
    },
  });