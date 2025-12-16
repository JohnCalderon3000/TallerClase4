package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoansPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // The app's loan page renders a form `#prestamoForm` directly; wait for it.
    private final By prestamoForm = By.id("prestamoForm");

    public LoansPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public void nuevaSolicitud() {
        // After navigating to the loans link, the loan form should appear.
        wait.until(ExpectedConditions.visibilityOfElementLocated(prestamoForm));
    }
}
