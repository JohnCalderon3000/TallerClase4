package tests;

import base.BaseTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import pages.DashboardPage;
import pages.LoginPage;
import pages.LoansPage;

package tests;

import base.BaseTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import pages.DashboardPage;
import pages.LoginPage;
import pages.LoansPage;

public class SolicitudPrestamoTest extends BaseTest {

    @Test
    public void solicitudPrestamoHappyPath() {
        LoginPage login = new LoginPage(driver, wait);
        login.open();
        login.login("qa.test@banco.com", "CypressTest2024!");

        DashboardPage dash = new DashboardPage(driver, wait);
        dash.waitForLoad();

        LoansPage loans = new LoansPage(driver, wait);
        loans.openNewLoan();
        loans.selectVehiculo();
        loans.fillLoan(5000000L, "24");
        loans.calculate();

        String cuotaText = loans.getCuotaText();
        String digits = cuotaText.replaceAll("[^0-9]", "");
        int cuota = digits.isEmpty() ? 0 : Integer.parseInt(digits);
        // approximate expected ~230000
        Assertions.assertThat(cuota).isGreaterThan(200000).isLessThan(300000);

        loans.acceptTermsAndSend();

        // Verify redirect to mis-solicitudes
        Assertions.assertThat(driver.getCurrentUrl()).contains("mis-solicitudes.html");
        // Basic check for new solicitud in list
        Assertions.assertThat(driver.getPageSource()).contains("5000000");
    }
}
