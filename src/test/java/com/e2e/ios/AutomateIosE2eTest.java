package com.e2e.ios;

import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Map;

import static org.openqa.selenium.Keys.TAB;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.testng.Assert.assertEquals;

public class AutomateIosE2eTest {

    private IOSDriver<MobileElement> driver;

    private static final String USERNAME = System.getenv("BROWSERSTACK_USERNAME");
    private static final String ACCESS_KEY = System.getenv("BROWSERSTACK_ACCESS_KEY");
    private static final String HUB_URL = "https://hub-cloud.browserstack.com/wd/hub";

    @BeforeMethod(alwaysRun = true)
    public void setup(Method m) throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("project", "Java MultiPlatform");
        caps.setCapability("build", "Scenario");
        caps.setCapability("name", m.getName());

        caps.setCapability("os_version", "16");
        caps.setCapability("device", "iPhone 14");
        caps.setCapability("fullContextList", true);
        //Make sure browserstack.appium_version is higher than 1.8.0
        //caps.setCapability("browserstack.appium_version", "1.8.0");

        caps.setCapability("browserstack.user", USERNAME);
        caps.setCapability("browserstack.key", ACCESS_KEY);
        caps.setCapability("browserstack.debug", true);
        caps.setCapability("browserstack.networkLogs", true);

        driver = new IOSDriver<>(new URL(HUB_URL), caps);
    }

    @Test
    public void bStackDemoLogin() {
        Wait<IOSDriver<MobileElement>> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .ignoring(NotFoundException.class);
        driver.activateApp("com.google.chrome.ios");
        wait.until(d -> d.getContextHandles().size() > 2);
        for (Object context : driver.getContextHandles()) {
            Map<String, String> contextMap = (Map<String, String>) context;
            if (contextMap.getOrDefault("url", "").equals("about://newtab/")) {
                driver.context(contextMap.get("id"));
                break;
            }
        }
        driver.get("https://bstackdemo.com");
        wait.until(elementToBeClickable(By.id("signin"))).click();
        wait.until(elementToBeClickable(By.cssSelector("#username input"))).sendKeys("fav_user" + TAB);
        driver.findElement(By.cssSelector("#password input")).sendKeys("testingisfun99" + TAB);
        driver.findElement(By.id("login-btn")).click();
        driver.findElement(By.xpath("//p[text() = 'iPhone XS']/../div[@class = 'shelf-item__buy-btn']")).click();
        driver.findElement(By.className("float-cart__close-btn")).click();
        driver.findElement(By.xpath("//p[text() = 'Galaxy S20']/../div[@class = 'shelf-item__buy-btn']")).click();
        wait.until(elementToBeClickable(By.className("buy-btn"))).click();
        wait.until(elementToBeClickable(By.id("firstNameInput"))).sendKeys("First");
        driver.findElement(By.id("lastNameInput")).sendKeys("Last");
        driver.findElement(By.id("addressLine1Input")).sendKeys("Test Address");
        driver.findElement(By.id("provinceInput")).sendKeys("Test Province");
        driver.findElement(By.id("postCodeInput")).sendKeys("123456");
        driver.findElement(By.id("checkout-shipping-continue")).click();
        String message = wait.until(elementToBeClickable(By.id("confirmation-message"))).getText();
        assertEquals(message, "Your Order has been successfully placed.", "Incorrect message");
        driver.findElement(By.cssSelector("div.continueButtonContainer button")).click();
        driver.findElement(By.id("orders")).click();
        wait.until(elementToBeClickable(By.className("order")));
        assertEquals(driver.findElements(By.className("order")).size(), 1, "Incorrect order");
        driver.findElement(By.id("logout")).click();
    }

    @AfterMethod(alwaysRun = true)
    public void closeDriver(ITestResult tr) {
        if (tr.isSuccess()) {
            driver.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"passed\"}}");
        } else {
            String reason = tr.getThrowable().toString().split("\\n")[0].replaceAll("[\\\\{}\"]", "");
            driver.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"failed\", \"reason\": \"" + reason + "\"}}");
        }
        driver.quit();
    }

}
