package com.appautomate.ios;

import com.utlis.AppUtils;
import com.utlis.SessionUtils;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

public class BaseTest {

    private static final String USERNAME = System.getenv("BROWSERSTACK_USERNAME");
    private static final String ACCESS_KEY = System.getenv("BROWSERSTACK_ACCESS_KEY");
    private static final String HUB_URL = "https://hub-cloud.browserstack.com/wd/hub";
    private IOSDriver<IOSElement> driver;

    public IOSDriver<IOSElement> getIOSDriver() {
        return driver;
    }

    @BeforeSuite(alwaysRun = true)
    public void setupApp() {
        AppUtils.uploadApp("iOSDemoApp", "ios/BStackSampleApp.ipa");
    }

    @BeforeMethod(alwaysRun = true)
    public void setupDriver(Method m) throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("project", "Java MultiPlatform");
        caps.setCapability("build", "Scenario");
        caps.setCapability("name", m.getName());

        caps.setCapability("device", "iPhone 14");
        caps.setCapability("os_version", "16");
        caps.setCapability("app", "iOSDemoApp");

        caps.setCapability("browserstack.user", USERNAME);
        caps.setCapability("browserstack.key", ACCESS_KEY);

        caps.setCapability("includeSafariInWebviews", true);
        caps.setCapability("fullContextList", true);

        driver = new IOSDriver<>(new URL(HUB_URL), caps);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult tr) {
        SessionUtils.markSession(driver, tr);
        driver.quit();
    }

}
