package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage extends BasePage {
    private By email = By.id("login-email");
    private By password = By.id("login-password");
    private By loginBtn = By.id("loginButton");

    public LoginPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void open() {
        driver.get("http://127.0.0.1:5500/frontend/login.html");
    }

    public void login(String user, String pass) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(email));
        driver.findElement(email).clear();
        driver.findElement(email).sendKeys(user);
        driver.findElement(password).clear();
        driver.findElement(password).sendKeys(pass);
        driver.findElement(loginBtn).click();
    }
}
package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage extends BasePage {
    private By email = By.id("login-email");
    private By password = By.id("login-password");
    private By loginBtn = By.id("loginButton");

    public LoginPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void open() {
        driver.get("http://127.0.0.1:5500/frontend/login.html");
    }

    public void login(String user, String pass) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(email)).clear();
        driver.findElement(email).sendKeys(user);
        driver.findElement(password).clear();
        driver.findElement(password).sendKeys(pass);
        driver.findElement(loginBtn).click();
    }
}
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
