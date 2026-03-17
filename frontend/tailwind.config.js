/** @type {import('tailwindcss').Config} */
const path = require('path');

module.exports = {
  content: [path.join(__dirname, 'src/**/*.{html,ts}')],
  theme: {
    extend: {
      colors: {
        primary: {
          50:  '#fff1f1',
          100: '#ffe1e1',
          200: '#ffc7c7',
          300: '#ffa0a0',
          400: '#ff6b6b',
          500: '#f83b3b',
          600: '#e51d1d',
          700: '#c11414',
          800: '#a01414',
          900: '#841818',
          950: '#480808',
        },
        dark: {
          900: '#0a0a0a',
          800: '#111111',
          700: '#1a1a1a',
          600: '#222222',
          500: '#2d2d2d',
          400: '#3d3d3d',
        }
      },
      fontFamily: {
        sans: ['Inter', 'sans-serif'],
      },
      backgroundImage: {
        'hero-pattern': "linear-gradient(135deg, rgba(10,10,10,0.97) 0%, rgba(26,10,10,0.95) 100%)",
      }
    },
  },
  plugins: [
    require('@tailwindcss/forms'),
  ],
};
