const { defineConfig } = require('cypress')

module.exports = defineConfig({
  e2e: {
    baseUrl: 'http://127.0.0.1:5500/frontend',
    supportFile: 'cypress/support/e2e.js',
    specPattern: 'cypress/e2e/**/*.cy.{js,ts}'
  },
  video: true,
  screenshotOnRunFailure: true,
  defaultCommandTimeout: 15000,
  requestTimeout: 15000,
  pageLoadTimeout: 60000,
  reporter: 'spec'
})
