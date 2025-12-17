package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Files;
import java.time.Duration;
import org.openqa.selenium.JavascriptExecutor;
import java.util.Map;

public class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Logger log = LoggerFactory.getLogger(getClass());

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions opts = new ChromeOptions();
        opts.addArguments("--headless=new");
        opts.addArguments("--no-sandbox");
        driver = new ChromeDriver(opts);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @AfterEach
    public void tearDown(TestInfo testInfo) {
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            // primary project screenshots inside selenium-project
            File destDir = new File("selenium-screenshots");
            destDir.mkdirs();
            String name = testInfo.getTestMethod().map(m -> m.getName()).orElse("test") + ".png";
            Files.copy(src.toPath(), new File(destDir, name).toPath());

            // also copy to parent project's screenshots directory as requested
            File parentDir = new File(".." + File.separator + "screenshots");
            parentDir.mkdirs();
            Files.copy(src.toPath(), new File(parentDir, name).toPath());
        } catch (Exception e) {
            log.warn("Could not take screenshot: {}", e.getMessage());
        }

        if (driver != null) {
            driver.quit();
        }
    }

    protected void seedAuth(String name) {
        try {
            driver.get("about:blank");
                String email = name.replaceAll("\\s","") + "@demo.com";
                String script = "window.localStorage.setItem('auth_token','fake-token');" +
                    "window.localStorage.setItem('user', JSON.stringify({id:1, nombre: arguments[0], email: arguments[1]}));";
                ((JavascriptExecutor) driver).executeScript(script, name, email);
        } catch (Exception e) {
            log.warn("Could not seed auth: {}", e.getMessage());
        }
    }
}
