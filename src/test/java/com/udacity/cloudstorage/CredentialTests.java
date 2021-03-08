package com.udacity.cloudstorage;

import com.udacity.cloudstorage.entity.Credential;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CredentialTests {

    protected WebDriver driver;

    public static final String URL = "https://www.thebeatles.com/";
    public static final String USERNAME = "khalil";
    public static final String PASSWORD = "khalef";
    public static final String URL_2 = "http://www.ringostarr.com/";
    public static final String USERNAME_2 = "ahmed";
    public static final String PASSWORD_2 = "khalef";

    @LocalServerPort
    protected int port;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
    }


    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }


    @Test
    public void testUserCreatCredential() {
        driver.get("http://localhost:" + this.port + "/signup");
        SignupPage signupPage = new SignupPage(driver);
        signupPage.setFirstName(USERNAME);
        signupPage.setLastName("khalef");
        signupPage.setUserName("ahmed");
        signupPage.setPassword(PASSWORD);
        signupPage.signUp();
        driver.get("http://localhost:" + this.port + "/login");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.setUserName(USERNAME);
        loginPage.setPassword(PASSWORD);

        HomePage homePage = new HomePage(driver);
        homePage.setCredentialUrl(URL);
        createAndVerifyCredential(URL, USERNAME, PASSWORD, homePage);




    }

    private void createAndVerifyCredential(String url, String username, String password, HomePage homePage) {
        createCredential(url, username, password, homePage);
        homePage.navToCredentialsTab();
        Credential credential = homePage.getFirstCredential();
        Assertions.assertEquals(url, credential.getUrl());
        Assertions.assertEquals(username, credential.getUsername());
        Assertions.assertNotEquals(password, credential.getPassword());
    }

    private void createCredential(String url, String username, String password, HomePage homePage) {
        homePage.navToCredentialsTab();
        homePage.addNewCredential();
        setCredentialFields(url, username, password, homePage);
        homePage.saveCredentialChanges();
        ResultPage resultPage = new ResultPage(driver);
        resultPage.clickOk();
        homePage.navToCredentialsTab();
    }

    private void setCredentialFields(String url, String username, String password, HomePage homePage) {
        homePage.setCredentialUrl(url);
        homePage.setCredentialUsername(username);
        homePage.setCredentialPassword(password);
    }
}
