import config.Config;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
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
    public void before() {
        log.info("Setup a driver");
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(false);
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        service = new GmailService();

        log.info("Start browser and open Ticketswap webpage");
        driver.get(URL);
        loginViaEmailAddress();
        mainPage = new MainPage(driver);
    }

    @After
    public void tearDown() {
        log.info("Set label Read to unread emails in Gmail inbox");
        service.markAsRead(SUBJECT_EMAIL_FOR_TICKET_SWAP);
        log.info("Close browser");
        driver.quit();
    }

    public void loginViaEmailAddress() {
        log.info("Open login form and send magicLink to email address");
        homePage = new HomePage(driver);
        homePage.openLoginForm();
        homePage.getMagicLink(EMAIL);

        log.info("Open new window and search for email in Gmail inbox");
        openMagicLinkFromEmail();
    }

    public void openMagicLinkFromEmail() {
        // Get TicketSwap email from Gmail inbox and open magic link
        driver.get(extractUrl());
        log.info("Magic link opened in browser window");
    }

    private String extractUrl() {
        log.info("Get TicketSwap email from Gmail inbox");
        // Get message body from new email with link
        String response = "";
        int count = 0;

        // Wait for email appear in Gmail inbox
        log.info("Wait for email to appear in Gmail inbox");
        while(!service.isMailExist(SUBJECT_EMAIL_FOR_TICKET_SWAP) || count > 20) {
            count++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("Waited {} sec for email to appear", count);
        }
        if(count >= 20 && !service.isMailExist(SUBJECT_EMAIL_FOR_TICKET_SWAP)) {
            throw new RuntimeException("Email doesn't received ");
        }
        log.debug("Get message body from email");
        response = service.emailsBody(SUBJECT_EMAIL_FOR_TICKET_SWAP);

        log.info("Get the magic link from received email");
        log.debug("By applying regex find first url in message");
        List<String> containedUrls = new ArrayList<>();
        String urlRegex = "((https?):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
        Matcher urlMatcher = pattern.matcher(response);

        while (urlMatcher.find()) {
            containedUrls.add(response.substring(urlMatcher.start(0),
                    urlMatcher.end(0)));
        }
        log.debug("Found magic url - {}", containedUrls.get(0));
        return containedUrls.get(0);
    }

    public static void switchToNewWindow(WebDriver driver) {
        String[] winHandles = driver.getWindowHandles().toArray(new String[] {});
        driver.switchTo().window(winHandles[winHandles.length - 1]);
    }
}
