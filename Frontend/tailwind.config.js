/** @type {import('tailwindcss').Config} */
const { nextui } = require("@nextui-org/react");

export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
    "./node_modules/@nextui-org/theme/dist/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    colors: {
      filtergrey: "#CFD8DC",
      white: "#FFFFFF",
      messageblue: "#218AFF",
      messagegrey: "#d8d8d8",
    },
    extend: {},
  },
  darkMode: "class",
  plugins: [nextui()],
};
