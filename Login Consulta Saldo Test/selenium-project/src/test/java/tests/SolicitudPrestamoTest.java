package tests;

import base.BaseTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import pages.LoansPage;
import pages.LoginPage;
import pages.DashboardPage;

public class SolicitudPrestamoTest extends BaseTest {

    @Test
    public void solicitudPrestamoHappyPath() {
        // seed auth and open loan page to avoid network login
        seedAuth("Loan User");
        LoansPage loans = new LoansPage(driver, wait);
        loans.openNewLoan();
        loans.selectVehiculo();
        loans.fillLoan(5000000L, "24");
        loans.calculate();

        String cuotaText = loans.getCuotaText();
        String digits = cuotaText.replaceAll("[^0-9]", "");
        int cuota = digits.isEmpty() ? 0 : Integer.parseInt(digits);
        Assertions.assertThat(cuota).isGreaterThan(200000).isLessThan(300000);

        loans.acceptTermsAndSend();

        Assertions.assertThat(driver.getCurrentUrl()).contains("mis_solicitudes");
        Assertions.assertThat(driver.getPageSource()).contains("5000000");
    }
}
