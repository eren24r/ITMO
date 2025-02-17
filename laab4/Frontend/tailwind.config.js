/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.{js,jsx,ts,tsx}",],
  theme: {
    screens: {
      sm: '754px',
      md: '754px',
      lg: '1210px',
      xl: '1210px',
    },
    extend: {
      colors: {
        'text-color': '#05060A',
        'background-color': '#F9FADD',
        'primary': '#5D66BD',
        'secondary': '#C6D000',
        'accent': '#78C18dd',
      },
    },
  },
  plugins: [],
}

