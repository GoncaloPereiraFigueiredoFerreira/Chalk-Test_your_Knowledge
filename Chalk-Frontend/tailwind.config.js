/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
    "node_modules/flowbite-react/**/*.{js,jsx,ts,tsx}",
    "node_modules/flowbite/**/*.{js,jsx,ts,tsx}",
  ],
  darkMode: "class",

  theme: {
    extend: {
      animation: {
        slide_in: "slide_in 1s easy-in",
      },
      keyframes: {
        slide_in: {
          "0%": {
            transform: "translateX(-100%)",
            opacity: "0",
          },
          "100%": {
            transform: "translateX(0)",
            opacity: "1",
          },
        },
      },
    },
    fontFamily: {
      pacifico: ["Pacifico", "sans-serif"],
      jetbrains: ["JetBrains Mono"],
    },
  },
  plugins: [require("@tailwindcss/forms"), require("flowbite/plugin")],
};
