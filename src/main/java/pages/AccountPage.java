package pages;

import common.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccountPage extends BasePage {

    @FindBy(css = "h1.css-54iqnz.eloqthd5")
    private WebElement fullName;
    @FindBy(xpath = "//p[@class='css-wtg550 eg1pfjs1'][1]")
    private WebElement emailAddress;

    public AccountPage(WebDriver driver) {
        super(driver);
    }

    public String getFullName() {
        return waitElementToAppear(fullName).getText();
    }

    public String getEmailAddressFromAccount() {
        return waitElementToAppear(emailAddress).getText();
    }

}
