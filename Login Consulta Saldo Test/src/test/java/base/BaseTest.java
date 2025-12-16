package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @BeforeEach
    public void setup() {
        logger.info("Setup ChromeDriver");
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=1920,1080");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    @AfterEach
    public void teardown() {
        if (driver != null) {
            driver.quit();
            logger.info("Driver quit");
        }
    }

    public void takeScreenshot(String name) {
        try {
            File dir = new File("screenshots");
            if (!dir.exists()) dir.mkdirs();
            String ts = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            Path dest = Path.of(dir.getAbsolutePath(), name + "_" + ts + ".png");
            TakesScreenshot tsShot = (TakesScreenshot) driver;
            File src = tsShot.getScreenshotAs(OutputType.FILE);
            Files.copy(src.toPath(), dest);
            logger.info("Saved screenshot: {}", dest.toString());
        } catch (IOException e) {
            logger.warn("Could not save screenshot: {}", e.getMessage());
        }
    }
}
