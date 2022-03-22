package com.e2e.ios;

import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Map;

import static org.openqa.selenium.Keys.ENTER;
import static org.openqa.selenium.Keys.TAB;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.testng.Assert.assertEquals;

public class ChromeE2eTest extends BaseTest {

    @Test
    @SuppressWarnings("unchecked")
    public void appBrowserE2eFlow() {
        IOSDriver<MobileElement> driver = getIOSDriver();
        Wait<IOSDriver<MobileElement>> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .ignoring(NotFoundException.class);

        driver.findElementByAccessibilityId("Text Button").click();
        driver.findElementByAccessibilityId("Text Input").click();
        driver.findElementByAccessibilityId("Text Input").sendKeys("Welcome to BrowserStack" + ENTER);
        assertEquals(driver.findElementByAccessibilityId("Text Output").getText(),
                "Welcome to BrowserStack", "Incorrect text");

        driver.activateApp("com.google.chrome.ios");
        wait.until(d -> d.getContextHandles().size() > 1);
        for (Object context : driver.getContextHandles()) {
            Map<String, String> contextMap = (Map<String, String>) context;
            if (contextMap.getOrDefault("url", "").equals("about://newtab/")) {
                driver.context(contextMap.get("id"));
            }
        }
        driver.get("https://bstackdemo.com");
        wait.until(elementToBeClickable(By.id("signin"))).click();
        wait.until(elementToBeClickable(By.cssSelector("#username input"))).sendKeys("fav_user" + TAB);
        driver.findElement(By.cssSelector("#password input")).sendKeys("testingisfun99" + TAB);
        driver.findElement(By.id("login-btn")).click();
        String username = wait.until(presenceOfElementLocated(By.className("username"))).getText();
        assertEquals(username, "fav_user", "Incorrect username");

        driver.activateApp("com.browserstack.Sample-iOS");
        driver.context("NATIVE_APP");

        driver.findElementByAccessibilityId("UI Elements").click();
        wait.until(d -> d.findElementByAccessibilityId("Text Button").isEnabled());
        driver.findElementByAccessibilityId("Text Button").click();
        driver.findElementByAccessibilityId("Text Input").click();
        driver.findElementByAccessibilityId("Text Input").sendKeys("Welcome to App-Automate" + ENTER);
        assertEquals(driver.findElementByAccessibilityId("Text Output").getText(),
                "Welcome to App-Automate", "Incorrect text");
    }

}
