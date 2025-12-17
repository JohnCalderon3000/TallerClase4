package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AccountsPage extends BasePage {
    private By accountNumber = By.id("cuenta-numero");
    private By balance = By.id("saldo-amount");

    public AccountsPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public String getMaskedAccount() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(accountNumber)).getText();
    }

    public String getBalanceText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(balance)).getText();
    }
}
