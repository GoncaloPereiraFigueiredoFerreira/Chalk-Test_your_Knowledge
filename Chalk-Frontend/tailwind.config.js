/** @type {import('tailwindcss').Config} */
export default {
  content: ["./index.html", "./src/**/*.{js,ts,jsx,tsx}"],
  darkMode: "class",
  theme: {
    extend: {},
    fontFamily: {
      pacifico: ["Pacifico", "sans-serif"],
      jetbrains: ["JetBrains Mono"],
    },
  },
  plugins: [require("@tailwindcss/forms")],
};
