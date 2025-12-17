import './commands'

// Screenshot on fail is enabled by config; optionally add behavior here
Cypress.on('test:after:run', (test, runnable) => {
  if (test.state === 'failed') {
    const title = `${runnable.parent.title} -- ${test.title}`
    cy.screenshot(title, { capture: 'runner' })
  }
})
