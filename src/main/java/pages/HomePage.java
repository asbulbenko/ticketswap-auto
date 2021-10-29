package pages;

import common.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {
    @FindBy(xpath = "//button[text()='Login']")
    private WebElement loginButton;
    @FindBy(xpath = "//a[text()='Sell tickets']")
    private WebElement sellTicketsButton;
    @FindBy(id = "site-search-input")
    private WebElement searchEventsField;
    @FindBy(xpath = "//button[text()='Continue with Facebook']")
    private WebElement loginViaFacebook;
    @FindBy(id = "email")
    private WebElement emailField;
    @FindBy(xpath = "//button[@type='submit']")
    private WebElement submitEmailButton;
    @FindBy(xpath = "//span[@class='css-4t59b0 e4e6mvv3']/button/span[@class='icon css-1v4zffw']")
    private WebElement closeModalButton;


    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void openLoginForm() {
        waitElementToBeClickable(loginButton).click();
    }

    public void openFacebookModalWindow() {
        waitElementToBeClickable(loginViaFacebook).click();
    }

    public void getMagicLink(String email) {
        waitElementToBeClickable(emailField).sendKeys(email);
        waitElementToBeClickable(submitEmailButton).click();
        waitElementToAppear(closeModalButton).click();
    }

}
