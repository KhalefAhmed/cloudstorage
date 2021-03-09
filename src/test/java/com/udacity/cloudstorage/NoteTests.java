package com.udacity.cloudstorage;

import com.udacity.cloudstorage.entity.Note;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NoteTests {

    public static final String USERNAME = "khalil";
    public static final String NOTE_TITLE = "NOTHING IMPORTANT";
    private static final String NOTE_DESCRIPTION = "NOTHING";
    public static final String PASSWORD = "khalef";
    protected WebDriver driver;

    @LocalServerPort
    protected int port;

    @BeforeAll
    static void beforeAll(){
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach(){
        this.driver = new ChromeDriver();
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    @AfterEach
    public void afterEach2() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testUserCreatNote() {

        driver.get("http://localhost:" + this.port + "/signup");
        SignupPage signupPage = new SignupPage(driver);
        signupPage.setFirstName(USERNAME);
        signupPage.setLastName("khalef");
        signupPage.setUserName(USERNAME);
        signupPage.setPassword(PASSWORD);
        signupPage.signUp();
        driver.get("http://localhost:" + this.port + "/login");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.setUserName(USERNAME);
        loginPage.setPassword(PASSWORD);
        loginPage.login();

        HomePage homePage = new HomePage(driver);
        homePage.navToNotesTab();
        homePage.addNewNote();
        homePage.setNoteTitle(NOTE_TITLE);
        homePage.setNoteDescription(NOTE_DESCRIPTION);
        homePage.saveNoteChanges();
        ResultPage resultPage = new ResultPage(driver);
        resultPage.clickOk();


        homePage.navToNotesTab();
        Note note = homePage.getFirstNote();
        Assertions.assertEquals(NOTE_TITLE, note.getNoteTitle());
        Assertions.assertEquals(NOTE_DESCRIPTION, note.getNoteDescription());

    }


}
