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
        driver.get("http://127.0.0.1:5500/frontend/solicitud-prestamo.html");
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
        driver.findElement(calculateBtn).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(cuota));
    }

    public String getCuotaText() {
        return driver.findElement(cuota).getText();
    }

    public void acceptTermsAndSend() {
        driver.findElement(termsCheckbox).click();
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
                driver.get("http://127.0.0.1:5500/frontend/solicitud-prestamo.html");
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
                driver.findElement(calculateBtn).click();
                wait.until(ExpectedConditions.visibilityOfElementLocated(cuota));
            }

            public String getCuotaText() {
                return driver.findElement(cuota).getText();
            }

            public void acceptTermsAndSend() {
                driver.findElement(termsCheckbox).click();
                driver.findElement(sendBtn).click();
            }
        }
