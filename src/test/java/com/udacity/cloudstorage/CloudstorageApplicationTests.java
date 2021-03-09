package com.udacity.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.concurrent.TimeUnit;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudstorageApplicationTests {

	protected WebDriver driver;

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
	public void testUserSignupLogin() {
		driver.get("http://localhost:" + this.port + "/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.setFirstName("khalil");
		signupPage.setLastName("khalef");
		signupPage.setUserName("ahmed");
		signupPage.setPassword("khalef");
		signupPage.signUp();
		driver.get("http://localhost:" + this.port + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.setUserName("ahmed");
		loginPage.setPassword("khalef");
		loginPage.login();

		Assertions.assertEquals("Home", driver.getTitle());
	}


	/**
	 * Write a test that signs up a new user, logs in, verifies that the home page is accessible, logs out, and verifies
	 * that the home page is no longer accessible.
	 */
	@Test
	public void testSignUpLoginLogout() {
		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());

		SignupPage signupPage = new SignupPage(driver);
		signupPage.setFirstName("John");
		signupPage.setLastName("Lennon");
		signupPage.setUserName("lennon");
		signupPage.setPassword("julia");
		signupPage.signUp();

		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());

		LoginPage loginPage = new LoginPage(driver);
		loginPage.setUserName("lennon");
		loginPage.setPassword("julia");
		loginPage.login();

		HomePage homePage = new HomePage(driver);
		homePage.logout();

		driver.get("http://localhost:" + this.port + "/home");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		Assertions.assertEquals("Login", driver.getTitle());
		loginPage.login();
		Assertions.assertNotEquals("Home",driver.getTitle());
	}




}
