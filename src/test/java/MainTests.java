import config.Config;
import lombok.extern.slf4j.Slf4j;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@Slf4j
public class MainTests extends BaseTest {

    @Rule
    public TestName name = new TestName();

    @Test
    public void testAbleToLoginAndOpenAccount() {
        // Able to open Account
        log.info("Start test {}", name.getMethodName());
        accountPage = mainPage.openAccountPage();
        assertThat("Username in account page is Oleksii Bulb",
                accountPage.getFullName(), is("Oleksii Bulb"));
        assertThat("Incorrect url opened for account page",
                driver.getCurrentUrl().endsWith("ticketswap.com/account"), is(true));
        assertThat("Correct email address appeared ", accountPage.getEmailAddressFromAccount(), is(Config.getEmail()));
    }

    @Test
    public void ableToSearchEventAddTicketToCartAndDelete() {
        log.info("Start test {}", name.getMethodName());

        // Find Kensigton Artist and open it's event
        log.info("Find artist Kensington in search field");
        mainPage.searchEvents("Kensington");
        log.info("Switch get notifications toggle");
        mainPage.getNotifiedAboutNewTickets();

        log.info("Get available type of ticket");
        mainPage.getAvailableTicketsType();
        mainPage.getFirstAvailableTicket();

        log.info("Click buy ticket and proceed to payment page");
        cartPage = mainPage.buyTicket();
        cartPage.continueToPaymentButton();

        assertThat("Text 'Payment method' absent on page",
                cartPage.getPaymentTitleText(), is("Payment method"));
        log.info("Delete tickets from cart");
        driver.navigate().back();
        cartPage.deleteTicketFromCart();
        assertThat("Text 'Your cart is empty' absent on page",
                cartPage.getEmptyCartTitleText(), is("Your cart is empty"));
    }

    @Test
    public void ableToSellTicket() {
        log.info("Start test {}", name.getMethodName());
        // Customer is able to interact with sell feature
        log.info("Open sell page & search for artist Kensington to sell");
        log.info("Add type of tickets");
        mainPage.openSellTicketsForm("Kensington");
        assertThat("Text 'Add your tickets' absent on page",
                mainPage.getAddTicketPageTitle(), is("Add your tickets"));

        log.info("Navigate back to sell page and delete it");
        driver.navigate().back();
        mainPage.deleteSellEventsFromPage();
    }
}
