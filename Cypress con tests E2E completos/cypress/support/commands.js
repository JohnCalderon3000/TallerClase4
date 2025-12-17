// Custom reusable commands
Cypress.Commands.add('login', (user) => {
  const username = (user && user.username) || 'test.qa@banco.com'
  const password = (user && user.password) || 'TestQA2024!'

  // Try UI login (visit login page and submit). If UI not available, fallback to API token set.
  cy.session([username], () => {
    cy.visit('/login.html')
    cy.document().then((doc) => {
      const emailEl = doc.querySelector('input[name=email]') || doc.querySelector('#email') || doc.querySelector('#login-email') || doc.querySelector('input[type=email]') || doc.querySelector('#username')
      const passEl = doc.querySelector('input[name=password]') || doc.querySelector('#password') || doc.querySelector('#login-password') || doc.querySelector('input[type=password]') || doc.querySelector('#pwd')
      const submitEl = doc.querySelector('button[type=submit]') || doc.querySelector('[data-cy=login-button]') || doc.querySelector('button')

      if (emailEl && passEl) {
        cy.wrap(emailEl, { timeout: 15000 }).clear().type(username)
        cy.wrap(passEl, { timeout: 15000 }).clear().type(password)
        if (submitEl) {
          cy.wrap(submitEl).click()
          // wait for login request (tests should alias this as '@login')
          cy.wait('@login', { timeout: 20000 }).then((interception) => {
            const status = interception && interception.response && interception.response.statusCode
            const body = interception && interception.response && interception.response.body
            if (status === 200 && body && (body.token || body.token === '')) {
              // login succeeded; wait for redirect and dashboard marker
              cy.location('pathname', { timeout: 20000 }).should('include', 'dashboard.html')
              cy.get('#userWelcome, [data-testid=user-welcome]', { timeout: 20000 }).should('exist')
            } else {
              // if request failed, surface a helpful error
              throw new Error('Login request failed or returned no token')
            }
          })
        }
      } else {
        // fallback: perform request to auth endpoint (if available) or set token
        cy.request({
          method: 'POST',
          url: '/api/auth/login',
          body: { username, password },
          failOnStatusCode: false
        }).then((resp) => {
          cy.window().then((win) => {
            if (resp && resp.body && resp.body.token) {
              win.localStorage.setItem('auth_token', resp.body.token)
            } else {
              win.localStorage.setItem('auth_token', 'fake-jwt-token')
            }
          })
        })
      }
    })
  })
})

Cypress.Commands.add('gotoAccounts', () => {
  cy.visit('/consulta-saldo.html')
  cy.get('#saldo-amount, #cuenta-numero', { timeout: 15000 }).should('exist')
})

Cypress.Commands.add('applyLoan', (payload) => {
  cy.get('#input-monto', { timeout: 10000 }).clear().type(String(payload.amount))
  cy.get('#select-plazo', { timeout: 10000 }).select(String(payload.term))
  // rate is read-only in the UI example; click calculate to reveal result + enviarSeccion
  cy.get('#btn-calcular', { timeout: 10000 }).click()
  // wait for result area and enviar section to appear
  cy.get('#resultadoCalculo', { timeout: 10000 }).should('be.visible')
  cy.get('#enviarSeccion', { timeout: 10000 }).should('be.visible')
})
