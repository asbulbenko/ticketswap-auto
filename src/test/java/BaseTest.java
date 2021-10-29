import config.Config;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.*;
import service.GmailService;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BaseTest {
    private static final String URL = Config.getAppUrl();
    private static final String EMAIL = Config.getEmail();
    private static final String SUBJECT_EMAIL_FOR_TICKET_SWAP = "Login link for TicketSwap";

    WebDriver driver;
    HomePage homePage;
    MainPage mainPage;
    AccountPage accountPage;
    Cart cartPage;
    GmailService service;



    @Before
    public void before() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(false);
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        service = new GmailService();

        driver.get(URL);
        loginViaEmailAddress();
        mainPage = new MainPage(driver);
    }

    @After
    public void tearDown() {
        // Set label Read to unread emails & close driver
        service.markAsRead(SUBJECT_EMAIL_FOR_TICKET_SWAP);
        driver.quit();
    }

    public void loginViaEmailAddress() throws InterruptedException {
        // Open login form and send magicLink to email address
        homePage = new HomePage(driver);
        homePage.openLoginForm();
        homePage.getMagicLink(EMAIL);

        // Open new window and search for email in Gmail inbox
        openMagicLinkFromEmail();
    }

    public void openMagicLinkFromEmail() {
        // Get TicketSwap email from Gmail inbox and open magic link
        driver.get(extractUrl());
    }

    private String extractUrl() {
        // Get message body from new email with link
        String response = "";
        int count = 0;

        // Wait for email appear in Gmail inbox
        while(!service.isMailExist(SUBJECT_EMAIL_FOR_TICKET_SWAP) || count > 20) {
            count++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(count >= 20 && !service.isMailExist(SUBJECT_EMAIL_FOR_TICKET_SWAP)) {
            throw new RuntimeException("Email doesn't received ");
        }

        response = service.emailsBody(SUBJECT_EMAIL_FOR_TICKET_SWAP);
        // By applying regex find first url in message
        List<String> containedUrls = new ArrayList<>();
        String urlRegex = "((https?):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
        Matcher urlMatcher = pattern.matcher(response);

        while (urlMatcher.find()) {
            containedUrls.add(response.substring(urlMatcher.start(0),
                    urlMatcher.end(0)));
        }
        return containedUrls.get(0);
    }

    public static void switchToNewWindow(WebDriver driver) {
        String[] winHandles = driver.getWindowHandles().toArray(new String[] {});
        driver.switchTo().window(winHandles[winHandles.length - 1]);
    }
}
