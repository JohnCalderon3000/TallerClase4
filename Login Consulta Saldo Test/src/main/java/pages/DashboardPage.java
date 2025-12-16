package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DashboardPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By misCuentasLink = By.cssSelector("a[data-testid='nav-cuentas']");
    private final By prestamosLink = By.cssSelector("a[data-testid='nav-prestamos']");

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public void goToMisCuentas() {
        wait.until(ExpectedConditions.elementToBeClickable(misCuentasLink)).click();
    }

    public void goToPrestamos() {
        wait.until(ExpectedConditions.elementToBeClickable(prestamosLink)).click();
    }
}
