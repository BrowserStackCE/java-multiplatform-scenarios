package com.appautomate.android;

import com.utlis.AppUtils;
import com.utlis.SessionUtils;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.openqa.selenium.Keys.TAB;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class AndroidE2ETest {

    private static final String USERNAME = System.getenv("BROWSERSTACK_USERNAME");
    private static final String ACCESS_KEY = System.getenv("BROWSERSTACK_ACCESS_KEY");
    private static final String HUB_URL = "https://hub.browserstack.com/wd/hub";
    private AndroidDriver<AndroidElement> driver;

    @BeforeSuite(alwaysRun = true)
    public void setupApp() {
        AppUtils.uploadApp("AndroidDemoApp", "android/WikipediaSample.apk");
    }

    @BeforeMethod(alwaysRun = true)
    public void setupDriver(Method m) throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("project", "Java MultiPlatform");
        caps.setCapability("build", "Scenario");
        caps.setCapability("name", m.getName());

        caps.setCapability("device", "Google Pixel 7");
        caps.setCapability("os_version", "13.0");
        caps.setCapability("app", "AndroidDemoApp");

        caps.setCapability("browserstack.user", USERNAME);
        caps.setCapability("browserstack.key", ACCESS_KEY);

        driver = new AndroidDriver<>(new URL(HUB_URL), caps);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void androidAppChromeE2eFlow() {
        Wait<AndroidDriver<AndroidElement>> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .ignoring(NotFoundException.class);

        driver.findElementByAccessibilityId("Search Wikipedia").click();
        MobileElement insertTextElement = wait.until(d -> d.findElementById("org.wikipedia.alpha:id/search_src_text"));
        insertTextElement.sendKeys("BrowserStack");
        wait.until(d -> d.findElementByClassName("android.widget.ListView").isDisplayed());
        List<String> companyNames = driver.findElementsByClassName("android.widget.TextView")
                .stream().map(MobileElement::getText).collect(toList());
        assertTrue(companyNames.contains("BrowserStack"), "Company is not present in the list");

        Activity activity = new Activity("com.android.chrome", "com.google.android.apps.chrome.Main");
        activity.setStopApp(false);
        driver.startActivity(activity);
        wait.until(d -> d.getContextHandles().contains("WEBVIEW_chrome"));
        driver.context("WEBVIEW_chrome");

        driver.get("https://bstackdemo.com");
        wait.until(elementToBeClickable(By.id("signin"))).click();
        wait.until(elementToBeClickable(By.cssSelector("#username input"))).sendKeys("fav_user" + TAB);
        driver.findElement(By.cssSelector("#password input")).sendKeys("testingisfun99" + TAB);
        driver.findElement(By.id("login-btn")).click();
        String username = wait.until(presenceOfElementLocated(By.className("username"))).getText();
        assertEquals(username, "fav_user", "Incorrect username");

        activity = new Activity("org.wikipedia.alpha", "org.wikipedia.main.MainActivity");
        activity.setStopApp(false);
        driver.startActivity(activity);
        driver.context("NATIVE_APP");

        driver.findElementByClassName("android.widget.ImageButton").click();
        driver.findElementByAccessibilityId("Search Wikipedia").click();
        insertTextElement = wait.until(d -> d.findElementById("org.wikipedia.alpha:id/search_src_text"));
        insertTextElement.sendKeys("Amazon");
        wait.until(d -> d.findElementByClassName("android.widget.ListView").isDisplayed());
        companyNames = driver.findElementsByClassName("android.widget.TextView")
                .stream().map(MobileElement::getText).collect(toList());
        assertTrue(companyNames.contains("Amazon"), "Company is not present in the list");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult tr) {
        SessionUtils.markSession(driver, tr);
        driver.quit();
    }

}
