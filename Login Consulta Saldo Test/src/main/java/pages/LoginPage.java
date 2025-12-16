package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.logging.Logger;

public class LoginPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final Logger logger = Logger.getLogger(LoginPage.class.getName());

    private final By inputEmail = By.id("login-email");
    private final By inputPassword = By.id("login-password");
    private final By btnEntrar = By.xpath("//button[@type='submit']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public void login(String email, String password) {
        logger.info("Login: " + email);
        WebElement e = wait.until(ExpectedConditions.visibilityOfElementLocated(inputEmail));
        e.clear();
        e.sendKeys(email);
        WebElement p = wait.until(ExpectedConditions.visibilityOfElementLocated(inputPassword));
        p.clear();
        p.sendKeys(password);
        wait.until(ExpectedConditions.elementToBeClickable(btnEntrar)).click();
    }
}
