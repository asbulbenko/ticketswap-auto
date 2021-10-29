package pages;

import common.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class MainPage extends BasePage {
    @FindBy(xpath = "//a[text()='Cart']")
    private WebElement cartButton;
    @FindBy(css = "button.css-tmcltr.e1oaf4hi7")
    private WebElement accountDropdown;
    @FindBy(xpath = "//a[@href='https://www.ticketswap.com/account']/span")
    private WebElement accountPageButton;
    @FindBy(id = "site-search-input")
    private WebElement searchEventsField;
    @FindBy(id = "site-search-item-0")
    private WebElement searchResultEvent1;
    @FindBy(id = "site-search-item-1")
    private WebElement searchResultEvent2;
    @FindBy(xpath = "//footer[@class='css-1nb2uwg eh8fd907']/span")
    private List<WebElement> countOfTickets;
    @FindBy(xpath = "//div[@class='e1sb6ph52 css-11h4g8u eh8fd9012'][1]") //(//div[@class='css-3ov7b7 eh8fd9011'])[1]
    private WebElement firstEventFromArtist;
    @FindBy(xpath = "//button[@role='switch']")
    private WebElement toggleAlertSwitch;
    @FindBy(xpath = "//ul[contains(@data-testid, 'available-tickets-list')]/li[1]")
    private WebElement firstAvailableTicket;
    @FindBy(xpath = "//button[text()='Buy ticket']")
    private WebElement buyButton;
    @FindBy(xpath = "//a[text()='Sell tickets']")
    private WebElement sellTicketsButton;
    @FindBy(xpath = "//button[@class='css-125u36o egdwx2f5'][1]")
    private WebElement eventToSellTicket;
    @FindBy(xpath = "//button[@class='css-1ik0mff egdwx2f5']")
    private WebElement eventToSellTicketSelected;
    @FindBy(xpath = "//button[@class='css-125u36o egdwx2f5'][2]")
    private WebElement eventTypeToSellTicket;
    @FindBy(xpath = "//button[text()='Continue']")
    private WebElement continueSellButton;
    @FindBy(xpath = "//h1[@class='css-1pg6rmv e1dq6rb51']")
    private WebElement addTicketsPageTitle;
    @FindBy(id = "query")
    private WebElement searchEventToSellField;
    @FindBy(xpath = "//h4[@class='css-15ic9n egdwx2f4'][1]")
    private WebElement listOfFoundSellEvents;
    @FindBy(xpath = "//span[@class='css-qim361 egdwx2f2'][1]")
    private List<WebElement> deleteSellEvents;

    public MainPage(WebDriver driver) {
        super(driver);
    }

    public AccountPage openAccountPage() {
        waitElementToBeClickable(accountDropdown).click();
        waitElementToBeClickable(accountPageButton).click();
        return new AccountPage(driver);
    }

    public void searchEvents(String text) {
        waitElementToBeClickable(searchEventsField).sendKeys(text);
        waitElementToAppear(searchResultEvent1);
        searchArtistAndOpen();
        getFirstEventFromArtist();
    }

    public void searchArtistAndOpen() {
        waitElementToAppear(searchResultEvent2);
        waitElementToBeClickable(searchResultEvent1).click();
    }

    public void searchSecondEventAndOpen() {
        waitElementToAppear(searchResultEvent2).click();
    }

    public void getFirstEventFromArtist() {
        waitElementToAppear(firstEventFromArtist);
        waitElementToBeClickable(firstEventFromArtist).click();
    }

    public void getNotifiedAboutNewTickets() {
        System.out.println("Found switch toggle");
        waitElementToAppear(toggleAlertSwitch);
        System.out.println("Found switch toggle will be clicked");
        waitElementToBeClickable(toggleAlertSwitch).click();
        System.out.println("Found switch toggle clicked");
    }

    public void getFirstAvailableTicket() {
        waitElementToBeClickable(firstAvailableTicket).click();
    }

    public Cart buyTicket() {
        waitElementToBeClickable(buyButton).click();
        return new Cart(driver);
    }

    public void getAvailableTicketsType() {
        List<WebElement> elementList =  waitUntilAllElementsAppear(countOfTickets);
        for (WebElement el : elementList) {
            if (Integer.parseInt(el.getText()) > 0) {
                System.out.println("Found available ticket");
                el.click();
                break;
            }
        }
    }

    public void openSellTicketsForm(String text) {
        waitElementToAppear(accountDropdown);
        // User logged in && Navigate to sell tickets and add events
        waitElementToBeClickable(sellTicketsButton).click();
        waitElementToBeClickable(searchEventToSellField).sendKeys(text);
        waitTextOnElementToAppear(listOfFoundSellEvents, text);
        waitElementToBeClickable(eventToSellTicket).click();
        waitElementToAppear(eventToSellTicketSelected);
        waitElementToBeClickable(eventTypeToSellTicket).click();
        waitElementToBeClickable(continueSellButton).click();
    }

    public void deleteSellEventsFromPage() {
        // Delete events in sell page
        List<WebElement> elementList = waitUntilAllElementsAppear(deleteSellEvents);
        elementList.get(0).click();
    }

    public String getAddTicketPageTitle() {
        return waitElementToAppear(addTicketsPageTitle).getText();
    }



}
