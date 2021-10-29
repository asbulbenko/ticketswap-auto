import config.Config;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class MainTests extends BaseTest {

    @Test
    public void testAbleToLoginAndOpenAccount() {
        // Able to open Account
        accountPage = mainPage.openAccountPage();
        assertThat("Username in account page is Oleksii Bulb",
                accountPage.getFullName(), is("Oleksii Bulb"));
        assertThat("Incorrect url opened for account page",
                driver.getCurrentUrl().endsWith("ticketswap.com/account"), is(true));
        assertThat("Correct email address appeared ", accountPage.getEmailAddressFromAccount(), is(Config.getEmail()));
    }

    @Test
    public void ableToSearchEventAddTicketToCartAndDelete() {
        // Find Kensigton Artist and open it's event
        mainPage.searchEvents("Kensington");
        // Switch get notifications toggle
        mainPage.getNotifiedAboutNewTickets();
        // Get available type of ticket
        mainPage.getAvailableTicketsType();
        mainPage.getFirstAvailableTicket();
        // Click buy and proceed to payment
        cartPage = mainPage.buyTicket();
        cartPage.continueToPaymentButton();
        assertThat("Text 'Payment method' absent on page",
                cartPage.getPaymentTitleText(), is("Payment method"));
        // Delete tickets from cart
        driver.navigate().back();
        cartPage.deleteTicketFromCart();
        assertThat("Text 'Your cart is empty' absent on page",
                cartPage.getEmptyCartTitleText(), is("Your cart is empty"));
    }

    @Test
    public void ableToSellTicket() {
        // Customer is able to interact with sell feature
        mainPage.openSellTicketsForm("Kensington");
        assertThat("Text 'Add your tickets' absent on page",
                mainPage.getAddTicketPageTitle(), is("Add your tickets"));
        // empty sell page from added events
        driver.navigate().back();
        mainPage.deleteSellEventsFromPage();
    }
}
