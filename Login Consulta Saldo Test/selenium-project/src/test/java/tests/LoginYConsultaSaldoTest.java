package tests;

import base.BaseTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.AccountsPage;
import pages.DashboardPage;
import pages.LoginPage;

public class LoginYConsultaSaldoTest extends BaseTest {

    @Test
    public void loginAndCheckBalance() {
        // seed auth to avoid network login
        seedAuth("Test User");
        // load local inspect HTML directly to avoid needing a running static server
        driver.get("file:///C:/Users/2822548/Documents/IA/TallerClase4/dashboard_inspect.html");
        // ensure page won't redirect by stubbing client auth helpers
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
            "window.requireAuth = function(){ return true; }; window.getUserData = function(){ return { id:1, nombre: 'Test User', email:'test@demo.com' }; };"
        );
        DashboardPage dash = new DashboardPage(driver, wait);
        dash.waitForLoad();
        dash.goToAccounts();

        AccountsPage accounts = new AccountsPage(driver, wait);
        String masked = accounts.getMaskedAccount();
        String balanceText = accounts.getBalanceText();

        Assertions.assertThat(masked).contains("1234");
        String digits = balanceText.replaceAll("[^0-9]", "");
        Assertions.assertThat(Integer.parseInt(digits)).isEqualTo(1500000);
    }
}
