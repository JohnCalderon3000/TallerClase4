package tests;

import base.BaseTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import pages.AccountsPage;
import pages.DashboardPage;
import pages.LoginPage;

public class LoginYConsultaSaldoTest extends BaseTest {

    @Test
    package tests;

    import base.BaseTest;
    import org.assertj.core.api.Assertions;
    import org.junit.jupiter.api.Test;
    import pages.AccountsPage;
    import pages.DashboardPage;
    import pages.LoginPage;

    public class LoginYConsultaSaldoTest extends BaseTest {

        @Test
        public void loginAndCheckBalance() {
            LoginPage login = new LoginPage(driver, wait);
            login.open();
            login.login("test.qa@banco.com", "TestQA2024!");

            DashboardPage dash = new DashboardPage(driver, wait);
            dash.waitForLoad();
            dash.goToAccounts();

            AccountsPage accounts = new AccountsPage(driver, wait);
            String masked = accounts.getMaskedAccount();
            String balanceText = accounts.getBalanceText();

            // Assertions
            Assertions.assertThat(masked).contains("1234");
            String digits = balanceText.replaceAll("[^0-9]", "");
            Assertions.assertThat(Integer.parseInt(digits)).isEqualTo(1500000);
        }
    }
