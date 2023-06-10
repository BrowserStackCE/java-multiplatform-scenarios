# Java MultiPlatform Scenarios <a href="https://www.browserstack.com/"><img src="https://www.vectorlogo.zone/logos/browserstack/browserstack-icon.svg" alt="BrowserStack" height="30"/></a> <a href="https://java.com"><img src="https://www.vectorlogo.zone/logos/java/java-icon.svg" alt="Java" height="30" /></a> <a href="https://www.selenium.dev/"><img src="https://seeklogo.com/images/S/selenium-logo-DB9103D7CF-seeklogo.com.png" alt="Selenium" height="30" /></a>

This repo provides an end-to-end user flow which includes a mobile app and a mobile browser in a single session on BrowserStack App-Automate. 

Steps performed during the execution of the test are mentioned below.

- Open the sample app and perform certain actions.
- Navigate to a mobile browser and login to the BStackDemo website.
- Then navigate back to the sample app and perform additional actions.

## Using Maven

### Setup

- Export the environment variables for the Username and Access Key of your BrowserStack account.
  ```sh
  export BROWSERSTACK_USERNAME=<browserstack-username> && export BROWSERSTACK_ACCESS_KEY=<browserstack-access-key>
  ```
- Clone the repo.
- Install dependencies.
  ```
  mvn compile
  ```
- Update the environment variables with your [BrowserStack Username and Access Key](https://www.browserstack.com/accounts/settings).

### Running your tests

- Run end-to-end test on Android device using Chrome mobile browser.
  ```
  mvn -Dtest=AndroidE2ETest test
  ```

- Run end-to-end test on iOS device using Safari mobile browser.
  ```
  mvn -Dtest=IosSafariE2ETest test
  ```

- Run end-to-end test on iOS device using Chrome mobile browser.
  ```
  mvn -Dtest=IosSafariE2ETest test
  ```

## Using Gradle

### Setup

- Clone the repo.
- Install dependencies.
  ```
  ./gradlew build
  ```
- Update the environment variables with your [BrowserStack Username and Access Key](https://www.browserstack.com/accounts/settings).

### Running your tests

- Run end-to-end test on Android device using Chrome mobile browser.
  ```
  ./gradlew test --tests AndroidE2ETest
  ```

- Run end-to-end test on iOS device using Safari mobile browser.
  ```
  ./gradlew test --tests IosSafariE2ETest
  ```

- Run end-to-end test on iOS device using Chrome mobile browser.
  ```
  ./gradlew test --tests IosChromeE2ETest
  ```

## Running your tests on Automate on iOS device in Chrome browser

- Using Maven
  ```
  mvn -Dtest=IosChromeTest test
  ```

- Using Gradle
  ```
  ./gradlew test --tests IosChromeTest
  ```

## Notes
- You can view your App-Automate test results on the [BrowserStack App-Automate dashboard](https://app-automate.browserstack.com/).
- Apps used to test on Android:
    - [Wikipedia Sample App](https://www.browserstack.com/app-automate/sample-apps/android/WikipediaSample.apk)
    - Chrome Mobile App
- Apps used to test on iOS:
    - [BStack Sample App](https://www.browserstack.com/app-automate/sample-apps/ios/BStackSampleApp.ipa)
    - Safari Mobile App
    - Chrome Mobile App
