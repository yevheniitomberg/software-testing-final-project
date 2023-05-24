package fun.tomberg.swedbankhm.controller;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Component;
import org.testng.Reporter;

@Component
public class TestHelper {
    private static final String BASE_PATH = "http://localhost:8081/";

    public static WebElement getById(ChromeDriver driver, String id) {
        return driver.findElement(By.id(id));
    }

    public static WebElement getByClassName(ChromeDriver driver, String className) {
        return driver.findElement(By.className(className));
    }
    public static WebElement getByName(ChromeDriver driver, String name) {
        return driver.findElement(By.name(name));
    }
    public static void clearAndSendKeys(WebElement element, String value) {
        element.clear();
        element.sendKeys(value);
    }

    public static void fillPasswords(ChromeDriver driver, String pass1, String pass2) {
        clearAndSendKeys(driver.findElement(By.id("inputPass")), pass1);
        clearAndSendKeys(driver.findElement(By.id("inputPass1")), pass2);
    }

    public static void removeUser(ChromeDriver driver, String email) {
        login(driver);
        getById(driver, "admin-panel").click();
        for (WebElement element : driver.findElements(By.name("userId"))) {
            if (element.getAttribute("data-username").equals(email) && element.getAttribute("data-active").equals("true")) {
                element.click();
            }
        }
        getById(driver, "delete-account").click();
        log("User removed");
    }

    public static void log(String s) {
        Reporter.log(s);
    }

    public static void login(ChromeDriver driver) {
        driver.get(BASE_PATH + "login");
        getById(driver, "username").sendKeys("yevhenii@tomberg.com");
        getById(driver, "password").sendKeys("password");
        getByClassName(driver, "btn").click();
        log("Logged in as an ADMIN");
    }
}
