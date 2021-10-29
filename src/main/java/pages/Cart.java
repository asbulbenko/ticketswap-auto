package pages;

import common.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Cart extends BasePage {
    @FindBy(xpath = "//button[text()='Continue']")
    private WebElement continueToPaymentButton;
    @FindBy(xpath = "//h1[text()='Payment method']")
    private WebElement paymentTitle;
    @FindBy(xpath = "//button[@class='css-15efeyk e59td2j1']")
    private WebElement trashButton;
    @FindBy(xpath = "//button[text()='Yes, go ahead']")
    private WebElement confirmModalDelete;
    @FindBy(xpath = "//h1[text()='Your cart is empty']")
    private WebElement emptyCartTitle;

    public Cart(WebDriver driver) {
        super(driver);
    }

    public void continueToPaymentButton() {
        waitElementToBeClickable(continueToPaymentButton).click();
    }

    public String getPaymentTitleText() {
        return waitElementToAppear(paymentTitle).getText();
    }

    public void deleteTicketFromCart() {
        waitElementToBeClickable(trashButton).click();
        waitElementToBeClickable(confirmModalDelete).click();
    }

    public String getEmptyCartTitleText() {
        return waitElementToAppear(emptyCartTitle).getText();
    }
}
