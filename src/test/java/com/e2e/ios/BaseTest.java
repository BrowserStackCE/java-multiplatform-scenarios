package com.e2e.ios;

import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.path.json.JsonPath;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class BaseTest {

    private static final ThreadLocal<IOSDriver<MobileElement>> driverThread = new ThreadLocal<>();

    private static final String USERNAME = System.getenv("BROWSERSTACK_USERNAME");
    private static final String ACCESS_KEY = System.getenv("BROWSERSTACK_ACCESS_KEY");
    private static final String URL = "http://hub-cloud.browserstack.com/wd/hub";

    public static IOSDriver<MobileElement> getIOSDriver() {
        return driverThread.get();
    }

    @BeforeSuite(alwaysRun = true)
    public void setupApp() {
        PreemptiveBasicAuthScheme authenticationScheme = new PreemptiveBasicAuthScheme();
        authenticationScheme.setUserName(USERNAME);
        authenticationScheme.setPassword(ACCESS_KEY);
        requestSpecification = new RequestSpecBuilder()
                .setBaseUri("https://api-cloud.browserstack.com")
                .setBasePath("app-automate")
                .setAuth(authenticationScheme)
                .build();
        responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();
        List<String> customIds = get("recent_apps").jsonPath().getList("custom_id");
        if (customIds == null || !customIds.contains("iOSDemoApp")) {
            System.out.println("Uploading app...");
            given()
                    .header("Content-Type", "multipart/form-data")
                    .multiPart("url", "https://www.browserstack.com/app-automate/sample-apps/ios/BStackSampleApp.ipa", "text")
                    .param("custom_id", "iOSDemoApp")
                    .post("upload");
        } else {
            System.out.println("Using previously uploaded app...");
        }
    }

    @BeforeMethod(alwaysRun = true)
    @Parameters({"config", "capability"})
    public void setupDriver(String configFile, String capability, Method m) throws MalformedURLException {
        JsonPath jsonPath = JsonPath.from(new File("src/test/resources/config/" + configFile + ".json"));
        Map<String, String> capabilitiesMap = new HashMap<>();
        capabilitiesMap.putAll(jsonPath.getMap("commonCapabilities"));
        capabilitiesMap.putAll(jsonPath.getMap("capabilities[" + capability + "]"));
        capabilitiesMap.put("name", m.getName() + " - " + capabilitiesMap.get("device"));
        capabilitiesMap.put("app", "iOSDemoApp");
        capabilitiesMap.put("browserstack.user", USERNAME);
        capabilitiesMap.put("browserstack.key", ACCESS_KEY);
        driverThread.set(new IOSDriver<>(new URL(URL), new DesiredCapabilities(capabilitiesMap)));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult tr) {
        IOSDriver<MobileElement> driver = driverThread.get();
        if (tr.isSuccess()) {
            driver.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"passed\"}}");
        } else {
            String reason = tr.getThrowable().getMessage().split("\\n")[0].replaceAll("[\\\\{}\"]", "");
            driver.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"failed\", \"reason\": \"" + reason + "\"}}");
        }
        driver.quit();
        driverThread.remove();
    }

}
