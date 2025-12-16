package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

public class LoginTest {
    private WebDriver driver;
    private LoginPage loginPage;
    private final Logger logger = Logger.getLogger(LoginTest.class.getName());

    @BeforeMethod
    public void setup() {
        logger.info("Setup: Configurando ChromeDriver");
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        if (Boolean.getBoolean("headless")) {
            options.addArguments("--headless=new");
        }
        options.addArguments("--window-size=1920,1080");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        driver.manage().window().maximize();
        loginPage = new LoginPage(driver);
        driver.get("http://127.0.0.1:5500/frontend/login.html");
        logger.info("Aplicación abierta en http://127.0.0.1:5500/frontend/login.html");
    }

    @Test(description = "Test Case: Login Exitoso con Credenciales Válidas")
    public void testLoginExitoso() {
        logger.info("Inicio del caso de prueba: Login exitoso");
        loginPage.enterEmail("test.qa@banco.com");
        loginPage.enterPassword("TestQA2024!");
        loginPage.clickEntrar();

        Assert.assertTrue(loginPage.isWelcomeDisplayed(), "El mensaje 'Bienvenido' debería mostrarse tras el login exitoso.");
        Assert.assertTrue(loginPage.isLogoutVisible(), "El botón 'Cerrar Sesión' debería ser visible tras el login exitoso.");
        logger.info("Asserts completados: login exitoso verificado");
    }

    @AfterMethod
    public void teardown(ITestResult result) {
        if (!result.isSuccess()) {
            logger.warning("Prueba fallida - tomando screenshot: " + result.getName());
            takeScreenshot(result.getName());
        }
        if (driver != null) {
            driver.quit();
            logger.info("Driver cerrado");
        }
    }

    private void takeScreenshot(String name) {
        try {
            File screenshotsDir = new File("screenshots");
            if (!screenshotsDir.exists()) screenshotsDir.mkdirs();
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            Path dest = Path.of(screenshotsDir.getAbsolutePath(), name + "_" + timestamp + ".png");
            TakesScreenshot ts = (TakesScreenshot) driver;
            File src = ts.getScreenshotAs(OutputType.FILE);
            Files.copy(src.toPath(), dest);
            logger.info("Screenshot guardado en: " + dest.toString());
        } catch (IOException e) {
            logger.warning("No se pudo guardar screenshot: " + e.getMessage());
        } catch (Exception e) {
            logger.warning("Error al tomar screenshot: " + e.getMessage());
        }
    }
}
