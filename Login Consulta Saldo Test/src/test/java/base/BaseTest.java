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
import java.nio.file.Files;
import java.time.Duration;

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
    public void tearDown(org.junit.jupiter.api.TestInfo testInfo) {
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destDir = new File("build/test-results/screenshots");
            destDir.mkdirs();
            String name = testInfo.getTestMethod().map(m -> m.getName()).orElse("test") + ".png";
            Files.copy(src.toPath(), new File(destDir, name).toPath());
        } catch (Exception e) {
            log.warn("Could not take screenshot: {}", e.getMessage());
        }

        if (driver != null) {
            driver.quit();
        }
    }
}
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
import java.time.Duration;

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
    public void tearDown(org.junit.jupiter.api.TestInfo testInfo) {
        try {
            // take screenshot
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destDir = new File("build/test-results/screenshots");
            destDir.mkdirs();
            String name = testInfo.getTestMethod().map(m -> m.getName()).orElse("test") + ".png";
            Files.copy(src.toPath(), new File(destDir, name).toPath());
        } catch (Exception e) {
            log.warn("Could not take screenshot: {}", e.getMessage());
        }

        if (driver != null) {
            driver.quit();
        }
    }
}
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
