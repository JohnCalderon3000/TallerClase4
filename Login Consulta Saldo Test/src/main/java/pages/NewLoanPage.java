package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class NewLoanPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By amountInput = By.id("input-monto");
    private final By termInput = By.id("select-plazo");
    // purpose in the app is a set of radio inputs; we'll select by name/value
    private final String purposeName = "proposito";
    private final By calculateBtn = By.id("btn-calcular");
    private final By monthlyPayment = By.id("cuota-mensual");
    private final By acceptTerms = By.id("checkbox-terminos");
    private final By submitBtn = By.id("btn-enviar-solicitud");

    public NewLoanPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public void fillLoan(String amount, String term, String purpose) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(amountInput)).clear();
        driver.findElement(amountInput).sendKeys(amount);
        Select sel = new Select(driver.findElement(termInput));
        sel.selectByValue(term);

        // choose radio by purpose value mapping
        String val = "personal";
        String p = purpose == null ? "" : purpose.toLowerCase();
        if (p.contains("veh")) val = "vehiculo";
        else if (p.contains("viv")) val = "vivienda";
        driver.findElement(By.cssSelector("input[name='" + purposeName + "'][value='" + val + "']")).click();
    }

    public void calculate() {
        wait.until(ExpectedConditions.elementToBeClickable(calculateBtn)).click();
    }

    public String getMonthlyPaymentText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(monthlyPayment)).getText();
    }

    public void acceptTerms() {
        wait.until(ExpectedConditions.elementToBeClickable(acceptTerms)).click();
    }

    public void submit() {
        wait.until(ExpectedConditions.elementToBeClickable(submitBtn)).click();
    }
}
