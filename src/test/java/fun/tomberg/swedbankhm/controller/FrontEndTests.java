package fun.tomberg.swedbankhm.controller;

import fun.tomberg.swedbankhm.service.implementation.ApiServiceImpl;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.boot.test.context.SpringBootTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

import static fun.tomberg.swedbankhm.controller.TestHelper.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@Test()
@SpringBootTest
public class FrontEndTests {

    private final ApiServiceImpl apiService = new ApiServiceImpl();

    private static final String BASE_PATH = "http://localhost:8085/";

    @BeforeClass
    public static void setUpSystemProperties() {
        System.setProperty("webdriver.chrome.driver","C:/Users/yevhe/University/Web Drivers/chromedriver.exe");
        System.setProperty("webdriver.http.factory", "jdk-http-client");
    }

    public ChromeDriver getDriver() {
        return new ChromeDriver();
    }

    @Test
    public void testAPIConnection() throws IOException {
        log("Making a simple request to the API");
        assertEquals("API error", apiService.getSpecificExchangeRate("EUR", "EUR", 1), Double.valueOf("1"));
        log("Connection is OK");
    }

    @Test
    public void registrationTest() {
        ChromeDriver driver = getDriver();
        driver.get(BASE_PATH + "register");
        log("Registration page opened");
        WebElement email = driver.findElement(By.id("inputEmail"));
        WebElement register = driver.findElement(By.id("register"));
        driver.findElement(By.id("inputFullName")).sendKeys("Shen Zih Han");
        email.sendKeys("someemail");
        fillPasswords(driver, "pass_1", "pass_1");
        register.click();
        assertTrue("Registration error", driver.getCurrentUrl().contains("register"));
        log("Email checked, passwords match but short");
        clearAndSendKeys(email, "some@email.com");
        fillPasswords(driver, "pass_1", "pass_1");
        register = driver.findElement(By.id("register"));
        register.click();
        assertTrue("Registration error", driver.getCurrentUrl().contains("register"));
        log("Email checked, passwords match but short");
        fillPasswords(driver, "pass_1_1", "pass_1_2");
        register = driver.findElement(By.id("register"));
        register.click();
        assertTrue("Registration error", driver.getCurrentUrl().contains("register"));
        log("Passwords not match");
        fillPasswords(driver, "pass_1_1", "pass_1_1");
        register = driver.findElement(By.id("register"));
        register.click();
        assertTrue("Registration error", driver.findElement(By.id("toast-element")) != null);
        log("Registration success");
        removeUser(driver, "some@email.com");
        log("Test result - OK");
        driver.quit();
    }

    @Test
    public void calculateCurrencyViaUI() throws Exception {
        ChromeDriver driver = getDriver();
        login(driver);
        driver.get(BASE_PATH + "currency");
        new Select(getById(driver, "currencyFromSelect")).selectByValue("UAH");
        new Select(getById(driver, "currencyToSelect")).selectByValue("EUR");
        getById(driver, "inputAmount").sendKeys("100");
        log("Currencies are selected");
        getById(driver, "subBtn").click();
        String [] splitResults = getByClassName(driver, "alert").getText().split("=");
        assertEquals("Not expected value", String.format("%.2f",apiService.getSpecificExchangeRate("UAH", "EUR", 1001)), splitResults[1].strip());
        log("Result is calculated");
        log("Test result is OK");
        driver.quit();
    }
}
