// postcss.config.cjs
module.exports = {
  plugins: {
    '@tailwindcss/postcss7-compat': {}, // For Tailwind v2 + PostCSS 7
    tailwindcss: {}, // For Tailwind v3 (if using latest)
    autoprefixer: {},
  },
};