package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AccountsPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Selectors adapted to the running application
    private final By balanceSpan = By.id("saldo-amount");
    private final By acctNumberSpan = By.id("cuenta-numero");

    public AccountsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public String getBalanceText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(balanceSpan)).getText();
    }

    public String getMaskedAccountNumber() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(acctNumberSpan)).getText();
    }
}
