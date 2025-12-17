package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DashboardPage extends BasePage {
    private By userWelcome = By.id("userWelcome");
    private By accountsLink = By.id("nav-cuentas");

    public DashboardPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void waitForLoad() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(userWelcome));
    }

    public void goToAccounts() {
        wait.until(ExpectedConditions.elementToBeClickable(accountsLink)).click();
    }
package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DashboardPage extends BasePage {
    private By userWelcome = By.id("userWelcome");
    private By accountsLink = By.id("nav-cuentas");

    public DashboardPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void waitForLoad() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(userWelcome));
    }

    public void goToAccounts() {
        wait.until(ExpectedConditions.elementToBeClickable(accountsLink)).click();
    }
}
