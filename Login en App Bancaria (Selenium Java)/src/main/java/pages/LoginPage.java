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
    private final By welcomeMsg = By.id("userWelcome");
    private final By btnLogout = By.className("btn-logout");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    // Note: 'Iniciar Sesión' button selector removed — use direct navigation or page state

    public void enterEmail(String email) {
        logger.info("Entering email: " + email);
        WebElement e = wait.until(ExpectedConditions.visibilityOfElementLocated(inputEmail));
        e.clear();
        e.sendKeys(email);
    }

    public void enterPassword(String password) {
        logger.info("Entering password");
        WebElement e = wait.until(ExpectedConditions.visibilityOfElementLocated(inputPassword));
        e.clear();
        e.sendKeys(password);
    }

    public void clickEntrar() {
        logger.info("Clicking 'Entrar'");
        wait.until(ExpectedConditions.elementToBeClickable(btnEntrar)).click();
    }

    public boolean isWelcomeDisplayed() {
        try {
            logger.info("Checking welcome message visibility");
            return wait.until(ExpectedConditions.visibilityOfElementLocated(welcomeMsg)).isDisplayed();
        } catch (Exception e) {
            logger.warning("Welcome message not found: " + e.getMessage());
            return false;
        }
    }

    public boolean isLogoutVisible() {
        try {
            logger.info("Checking logout button visibility");
            return wait.until(ExpectedConditions.visibilityOfElementLocated(btnLogout)).isDisplayed();
        } catch (Exception e) {
            logger.warning("Logout button not found: " + e.getMessage());
            return false;
        }
    }
}
