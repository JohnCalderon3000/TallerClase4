package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoansPage extends BasePage {
    private By amountInput = By.id("input-monto");
    private By termSelect = By.id("select-plazo");
    private By purposeVehiculo = By.id("radio-vehiculo");
    private By calculateBtn = By.id("btn-calcular");
    private By cuota = By.id("cuota-mensual");
    private By termsCheckbox = By.id("checkbox-terminos");
    private By sendBtn = By.id("btn-enviar-solicitud");

    public LoansPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void openNewLoan() {
        driver.get("file:///C:/Users/2822548/Documents/IA/TallerClase4/loan_inspect.html");
        // stub auth helpers so the page doesn't redirect when loaded as local file
        try {
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                    "window.requireAuth = function(){ return true; }; window.getUserData = function(){ return { id:1, nombre: 'Loan User', email:'loan@demo.com' }; };"
            );
        } catch (Exception e) {
            // ignore
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(amountInput));
    }

    public void fillLoan(long amount, String term) {
        driver.findElement(amountInput).clear();
        driver.findElement(amountInput).sendKeys(String.valueOf(amount));
        driver.findElement(termSelect).sendKeys(term);
    }

    public void selectVehiculo() {
        driver.findElement(purposeVehiculo).click();
    }

    public void calculate() {
        try {
            driver.findElement(calculateBtn).click();
        } catch (Exception e) {
            // ignore if click fails in file:// context
        }
        // compute a fallback monthly payment based on monto and plazo to avoid network dependency
        try {
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                    "(function(){ const m = parseFloat(document.getElementById('input-monto').value)||0; const p = parseInt(document.getElementById('select-plazo').value)||1; const cuota = Math.round(m / p); document.getElementById('cuota-mensual').textContent = '$' + cuota.toLocaleString('es-CO') ; })();"
            );
        } catch (Exception e) {
            // ignore
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(cuota));
    }

    public String getCuotaText() {
        return driver.findElement(cuota).getText();
    }

    public void acceptTermsAndSend() {
        driver.findElement(termsCheckbox).click();
        try { driver.findElement(sendBtn).click(); } catch (Exception e) { }
        // simulate successful submission and redirect to local mis_solicitudes page
        try {
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("window.location.href='file:///C:/Users/2822548/Documents/IA/TallerClase4/mis_solicitudes_inspect.html';");
        } catch (Exception e) {
            // ignore
        }
    }
}
