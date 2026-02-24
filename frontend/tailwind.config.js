module.exports = {
  content: [
    './shop/src/**/*.{html,ts,scss}',
    './admin/src/**/*.{html,ts,scss}',
    './libs/**/*.{html,ts,scss}'
  ],
  theme: {
    extend: {
      colors: {
        brand: {
          50: '#f3faf7',
          100: '#ddf4e8',
          500: '#14b86f',
          700: '#0f8f56',
          900: '#0a5032'
        }
      }
    }
  },
  plugins: []
};
