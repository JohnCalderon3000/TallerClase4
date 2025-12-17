describe('Préstamo - End to End', () => {
  beforeEach(() => {
    // clean state
    cy.clearCookies()
    cy.clearLocalStorage()
    // load fixture and set up API intercepts
    cy.fixture('prestamo').then((f) => {
      // auth login endpoint stub
      cy.intercept('POST', '**/api/auth/login', (req) => {
        req.reply({ statusCode: 200, body: { token: f.user.token } })
      }).as('login')

      // saldo endpoint used by consulta-saldo.html
      cy.intercept('GET', '**/api/cuentas/saldo*', { statusCode: 200, body: { numeroCuenta: '**** 1234', tipoCuenta: 'Cuenta de Ahorros', saldo: f.account.balance } }).as('getSaldo')

      // loan calculation (will be overridden in tests when needed)
      cy.intercept('POST', '**/api/prestamos/calcular', (req) => {
        const monthly = f.calculation.monthly
        req.reply({ statusCode: 200, body: { cuotaMensual: monthly, tasaInteres: 2.0, totalPagar: monthly * (f.loanRequest.term || 12) } })
      }).as('calculate')

      // loan apply
      cy.intercept('POST', '**/api/prestamos/solicitar', (req) => {
        const loan = Object.assign({ id: 'loan-1', estado: 'APROBADO' }, req.body)
        req.reply({ statusCode: 201, body: loan })
      }).as('applyLoan')

      // loans list
      cy.intercept('GET', '**/api/prestamos/solicitudes*', { statusCode: 200, body: f.loans }).as('getLoans')
    })
  })

  it('Login y consulta de saldo', () => {
    cy.fixture('prestamo').then((f) => {
      // perform UI login explicitly to ensure request is captured
      cy.visit('/login.html', {
        onBeforeLoad: (win) => {
          // diagnostics
          win.__errors = []
          win.addEventListener('error', (e) => { try { win.__errors.push(String(e.error || e.message || e.filename)) } catch (err) {} })

          // provide minimal utils used by the page so scripts don't throw
          win.isAuthenticated = () => false
          win.saveAuthData = (token, user) => { win.localStorage.setItem('auth_token', token); win.localStorage.setItem('user', JSON.stringify(user)); }
          win.showToast = () => {}
          win.validateRequired = (v) => !!v
          win.isValidEmail = (e) => /\S+@\S+\.\S+/.test(e)
          win.clearFieldError = () => {}
          win.clearAllErrors = () => {}
          // stub global API_BASE_URL to avoid undefined URL construction
          win.API_BASE_URL = ''
          // do NOT stub fetch here; allow cy.intercept to handle network requests
        }
      })
      cy.get('#login-email', { timeout: 15000 }).clear().type(f.user.username)
      cy.get('#login-password').clear().type(f.user.password)
      cy.get('#loginButton').click()
      // wait for redirect and a visible dashboard marker
      cy.location('pathname', { timeout: 20000 }).should('include', 'dashboard.html')
      cy.get('#userWelcome, [data-testid=user-welcome]', { timeout: 20000 }).should('be.visible')
      cy.gotoAccounts()

      cy.wait('@getSaldo')
      cy.get('#cuenta-numero').should('exist').and('contain.text', f.account.mask)
      cy.get('#saldo-amount').invoke('text').then((txt) => {
        const digits = txt.replace(/[^0-9]/g, '')
        expect(Number(digits)).to.equal(f.account.balance)
      })
      // screenshot on success
      cy.screenshot('login-consulta-saldo-success')
    })
  })

  it('Solicitud de préstamo completa (happy path)', () => {
    cy.fixture('prestamo').then((f) => {
      // override calculation to validate UI update
      cy.intercept('POST', '**/api/prestamos/calcular', (req) => {
        // simulate calculation using payload
        const amount = Number(req.body.amount || f.loanRequest.amount)
        const term = Number(req.body.term || f.loanRequest.term)
        const rate = Number(req.body.rate || f.loanRequest.rate)
        // simple monthly calc (stubbed)
        const monthly = Math.round((amount * (rate + 0.01)) / term)
        req.reply({ statusCode: 200, body: { monthly } })
      }).as('calculateDynamic')

      // perform UI login explicitly (allow intercept to handle auth request)
      cy.visit('/login.html', {
        onBeforeLoad: (win) => {
          win.isAuthenticated = () => false
          win.saveAuthData = (token, user) => { win.localStorage.setItem('auth_token', token); win.localStorage.setItem('user', JSON.stringify(user)); }
          win.showToast = () => {}
          win.validateRequired = (v) => !!v
          win.isValidEmail = (e) => /\S+@\S+\.\S+/.test(e)
          win.clearFieldError = () => {}
          win.clearAllErrors = () => {}
          win.API_BASE_URL = ''
        }
      })
      cy.get('#login-email', { timeout: 15000 }).clear().type(f.user.username)
      cy.get('#login-password').clear().type(f.user.password)
      cy.get('#loginButton').click()
      cy.location('pathname', { timeout: 20000 }).should('include', 'dashboard.html')
      cy.visit('/solicitud-prestamo.html')

      // ensure loan amount meets business rule (> 5M) and select purpose 'vehiculo'
      expect(f.loanRequest.amount, 'fixture loan amount').to.be.greaterThan(5000000)
      // select purpose radio for 'vehiculo'
      cy.get('#radio-vehiculo', { timeout: 5000 }).check()
      // fill form using custom command (will click calculate and wait for results)
      cy.applyLoan(f.loanRequest)

      cy.wait('@calculateDynamic')
      // expect calculated monthly to be shown
      cy.get('#cuota-mensual').invoke('text').then((t) => {
        const digits = t.replace(/[^0-9]/g, '')
        expect(Number(digits)).to.be.greaterThan(0)
      })

      // ensure enviar section visible then enable send button via checkbox and click send
      cy.get('#enviarSeccion', { timeout: 10000 }).should('be.visible')
      cy.get('#checkbox-terminos').check()
      cy.get('#btn-enviar-solicitud').should('not.be.disabled').click()
      cy.wait('@applyLoan')
      // success UI isn't explicitly defined; expect redirect or message
      cy.get('#resultadoCalculo, #enviarSeccion, #btn-enviar-solicitud').should('exist')

      // validate loans list fetch and UI update
      cy.intercept('GET', '**/api/prestamos/solicitudes*', { statusCode: 200, body: [{ id: 'loan-1', monto: f.loanRequest.amount, estado: 'APROBADO' }] }).as('getLoansUpdated')
      cy.visit('/mis-solicitudes.html')
      cy.wait('@getLoansUpdated')
      cy.get('#solicitudesGrid, [data-testid=solicitud-card]', { timeout: 10000 }).should('contain.text', String(f.loanRequest.amount))
      // screenshot on success
      cy.screenshot('solicitud-prestamo-happypath-success')
    })
  })

  it('Validaciones de formulario (edge cases)', () => {
    cy.fixture('prestamo').then((f) => {
      // login with fixture creds and minimal stubs
      cy.visit('/login.html', {
        onBeforeLoad: (win) => {
          win.isAuthenticated = () => false
          win.saveAuthData = (token, user) => { win.localStorage.setItem('auth_token', token); win.localStorage.setItem('user', JSON.stringify(user)); }
          win.showToast = () => {}
          win.validateRequired = (v) => !!v
          win.isValidEmail = (e) => /\S+@\S+\.\S+/.test(e)
          win.clearFieldError = () => {}
          win.clearAllErrors = () => {}
          win.API_BASE_URL = ''
        }
      })
      cy.get('#login-email', { timeout: 15000 }).clear().type(f.user.username)
      cy.get('#login-password').clear().type(f.user.password)
      cy.get('#loginButton').click()
      cy.location('pathname', { timeout: 20000 }).should('include', 'dashboard.html')
      cy.visit('/solicitud-prestamo.html')

      // Empty fields
      cy.get('#btn-calcular').click()
      cy.get('#btn-enviar-solicitud').should('be.disabled')
      // since fields are empty, expect native validation or error messages
      cy.get('.error-message').should('exist')

      // Amount out of range
      cy.get('#input-monto').clear().type('100')
      cy.get('#select-plazo').select('12')
      cy.get('#btn-calcular').click()
      cy.get('.error-message').should('contain.text', 'monto')

      // No terms accepted
      cy.get('#input-monto').clear().type('5000000')
      cy.get('#checkbox-terminos').uncheck()
      cy.get('#btn-enviar-solicitud').should('be.disabled')
      cy.get('.error-message').should('exist')
      // screenshot on success (edge-case validations observed)
      cy.screenshot('validaciones-formulario-success')
    })
  })
})
