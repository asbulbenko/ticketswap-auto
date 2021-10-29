package common;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BasePage {

    public WebDriver driver;
    WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = (WebDriverWait) new WebDriverWait(driver, Duration.ofSeconds(50)).pollingEvery(Duration.ofSeconds(3));
    }

    /**
     * Waiting methods
     */
    public WebElement waitElementToAppear(WebElement element) {
        try {
            return wait.until(webDriver -> ExpectedConditions.visibilityOf(element).apply(webDriver));
        } catch (TimeoutException e) {
            throw new IllegalArgumentException("element " + element.getText() + "wasn't visible on the page", e);
        }
    }

    public Boolean waitTextOnElementToAppear(WebElement element, String text) {
        try {
            return wait.until(webDriver -> ExpectedConditions.textToBePresentInElement(element, text).apply(webDriver));
        } catch (TimeoutException e) {
            throw new IllegalArgumentException("element text - " + text + "wasn't present in the element " + element.getText(), e);
        }
    }

    public List<WebElement> waitUntilAllElementsAppear(List<WebElement> webElements) {
        try {
            return wait.until(webDriver -> ExpectedConditions.visibilityOfAllElements(webElements).apply(webDriver));
        } catch (TimeoutException e) {
            throw new IllegalArgumentException("List of elements not appeared");
        }
    }

    public WebElement waitElementToBeClickable(WebElement element) {
        try {
            return wait.ignoring(NoSuchElementException.class)
                    .until(webDriver -> ExpectedConditions.elementToBeClickable(element)
                            .apply(webDriver));
        } catch (TimeoutException e) {
            throw new IllegalArgumentException("element " + element.getText() + "wasn't clickable", e);
        }
    }
}
