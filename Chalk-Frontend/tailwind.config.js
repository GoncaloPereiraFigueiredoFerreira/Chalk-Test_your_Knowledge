/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
    "./node_modules/flowbite/**/*.js",
    "node_modules/flowbite-react/**/*.{js,jsx,ts,tsx}",
  ],
  darkMode: "class",

  theme: {
    extend: {},
    fontFamily: {
      pacifico: ["Pacifico", "sans-serif"],
      jetbrains: ["JetBrains Mono"],
    },
  },
  plugins: [require("@tailwindcss/forms"), require("flowbite/plugin")],
};
