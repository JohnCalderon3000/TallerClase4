package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class RequestsPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // cards with data-testid='solicitud-card' (app renders div.solicitud-card)
    private final By requestRows = By.cssSelector("div[data-testid='solicitud-card']");
    private final By solicitudesContainer = By.id("solicitudesContainer");
    private final By emptyState = By.id("emptyState");
    private final By errorState = By.id("errorState");

    public RequestsPage(WebDriver driver) {
        this.driver = driver;
        // increase default wait to 10s for dynamic content
        // increase default wait to 15s for dynamic content
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public List<String> listRequestIds() {
        // Wait for either the container with solicitudes, the empty state, or an error state to appear
        wait.withTimeout(java.time.Duration.ofSeconds(15)).until(driver ->
            driver.findElements(solicitudesContainer).size() > 0
                || driver.findElements(emptyState).size() > 0
                || driver.findElements(errorState).size() > 0);

        return driver.findElements(requestRows).stream()
                .map(card -> {
                    try {
                        return card.findElement(By.cssSelector(".numero-solicitud")).getText().trim();
                    } catch (Exception ex) {
                        return "";
                    }
                })
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }
}
