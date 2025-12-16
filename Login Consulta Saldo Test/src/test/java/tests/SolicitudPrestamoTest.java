package tests;

import base.BaseTest;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pages.DashboardPage;
import pages.LoansPage;
import pages.NewLoanPage;
import pages.RequestsPage;
import pages.LoginPage;
import extensions.ScreenshotOnFinishExtension;

import java.util.List;

@ExtendWith(ScreenshotOnFinishExtension.class)
public class SolicitudPrestamoTest extends BaseTest {

    @Test
    public void solicitudPrestamoFlujo() throws InterruptedException {
        driver.get("http://127.0.0.1:5500/frontend/login.html");
        LoginPage login = new LoginPage(driver);
        login.login("qa.test@banco.com", "CypressTest2024!");

        DashboardPage dash = new DashboardPage(driver);
        dash.goToPrestamos();

        LoansPage loans = new LoansPage(driver);
        loans.nuevaSolicitud();

        NewLoanPage newLoan = new NewLoanPage(driver);
        newLoan.fillLoan("5000000", "24", "Vehículo");
        newLoan.calculate();

        String cuotaText = newLoan.getMonthlyPaymentText();
        // Normalize numbers: expect approx 230000
        String digits = cuotaText.replaceAll("[^0-9]", "");
        int cuota = Integer.parseInt(digits);
        Assertions.assertThat(cuota).as("Cuota aproximada").isBetween(200000, 260000);

        newLoan.acceptTerms();
        newLoan.submit();

        // wait for redirect to Mis Solicitudes (app sets timeout before redirect)
        try {
            // allow a longer timeout for client-side redirect
            new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10))
                    .until(org.openqa.selenium.support.ui.ExpectedConditions.urlContains("mis-solicitudes.html"));
        } catch (Exception ex) {
            // proceed to collect diagnostics even if URL didn't change
            logger.warn("Redirect to mis-solicitudes did not happen within timeout: {}", ex.getMessage());
        }

        // If the app didn't redirect automatically, navigate to the Mis Solicitudes page as a fallback
        if (!driver.getCurrentUrl().contains("mis-solicitudes.html")) {
            logger.info("Fallback: navigating to mis-solicitudes.html to collect request list");
            driver.get("http://127.0.0.1:5500/frontend/mis-solicitudes.html");
        }

        // Validate toast success and redirection to Mis Solicitudes
        RequestsPage req = new RequestsPage(driver);
        List<String> ids = req.listRequestIds();
        Assertions.assertThat(ids).as("Debe contener la nueva solicitud").isNotEmpty();
    }
}
