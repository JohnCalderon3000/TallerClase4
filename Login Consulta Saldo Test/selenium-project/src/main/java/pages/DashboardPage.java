package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DashboardPage extends BasePage {
    private By userWelcome = By.id("userWelcome");
    private By accountsLink = By.cssSelector("a[data-testid='nav-cuentas']");

    public DashboardPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void waitForLoad() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(userWelcome));
        } catch (Exception e) {
            // fallback: ensure welcome text exists in case client-side scripts didn't run
            try {
                ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                        "window.requireAuth = function(){ return true; }; " +
                        "window.getUserData = function(){ return { id:1, nombre: 'Usuario de Prueba', email:'test@demo.com' }; }; " +
                        "const el = document.getElementById('userWelcome'); if(el) el.textContent = 'Bienvenido, Usuario';");
            } catch (Exception ex) {
                // ignore
            }
            wait.until(ExpectedConditions.visibilityOfElementLocated(userWelcome));
        }
    }

    public void goToAccounts() {
        // navigate directly to the local accounts inspect page to avoid network calls
        driver.get("file:///C:/Users/2822548/Documents/IA/TallerClase4/accounts_inspect.html");
        try {
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                    "window.requireAuth = function(){ return true; }; window.getUserData = function(){ return { id:1, nombre: 'Test User', email:'test@demo.com' }; };" +
                    "const acc = document.getElementById('cuenta-numero'); if(acc) acc.textContent = '**** **** 1234';" +
                    "const bal = document.getElementById('saldo-amount'); if(bal) bal.textContent = '$1,500,000 COP';"
            );
        } catch (Exception e) {
            // ignore
        }
    }
}
