package tests;

import base.BaseTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pages.AccountsPage;
import pages.DashboardPage;
import pages.LoginPage;
import extensions.ScreenshotOnFinishExtension;

@ExtendWith(ScreenshotOnFinishExtension.class)
public class LoginYConsultaSaldoTest extends BaseTest {

    @Test
    public void loginAndCheckBalance() {
        driver.get("http://127.0.0.1:5500/frontend/login.html");
        LoginPage login = new LoginPage(driver);
        login.login("test.qa@banco.com", "TestQA2024!");

        DashboardPage dash = new DashboardPage(driver);
        dash.goToMisCuentas();

        AccountsPage accounts = new AccountsPage(driver);
        String balance = accounts.getBalanceText();
        String acctMask = accounts.getMaskedAccountNumber();

        // Assertions: normalize balance to digits and compare numeric value (robust to locale formats)
        String digits = balance.replaceAll("[^0-9]", "");
        int balanceAmount = digits.isEmpty() ? 0 : Integer.parseInt(digits);
        Assertions.assertThat(balanceAmount).as("Saldo debe ser 1,500,000 COP").isEqualTo(1500000);

        // Account masking: assert there are last 4 digits present and masked pattern exists
        String acctDigits = acctMask.replaceAll("[^0-9]", "");
        Assertions.assertThat(acctDigits.length()).as("Número de cuenta debe mostrar 4 dígitos finales").isEqualTo(4);
    }
}
