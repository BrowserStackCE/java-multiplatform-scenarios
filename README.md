# Java MultiPlatform Scenarios <a href="https://www.browserstack.com/"><img src="https://www.vectorlogo.zone/logos/browserstack/browserstack-icon.svg" alt="BrowserStack" height="30"/></a> <a href="https://java.com"><img src="https://www.vectorlogo.zone/logos/java/java-icon.svg" alt="Java" height="30" /></a> <a href="https://www.selenium.dev/"><img src="https://seeklogo.com/images/S/selenium-logo-DB9103D7CF-seeklogo.com.png" alt="Selenium" height="30" /></a>

Automate a real end-to-end user flow between app and browser in a single session. 

Steps performed during the execution of the test are mentioned below.

- Open the sample app and perform certain actions.
- Navigate to a mobile browser and login to the BStackDemo website.
- Then navigate back to the sample app and perform additional actions.

## Using Maven

### Setup

- Clone the repo
- Install dependencies
  ```
  mvn compile
  ```
- Update the environment variables with your [BrowserStack Username and Access Key](https://www.browserstack.com/accounts/settings)

### Running your tests

#### Android

- Run a single end-to-end test using Chrome mobile browser
  ```
  mvn -P android-chrome-e2e-single test
  ```

- Run parallel end-to-end tests using Chrome mobile browser
  ```
  mvn -P android-chrome-e2e-parallel test
  ```

#### iOS

- Run a single end-to-end test using Safari mobile browser
  ```
  mvn -P ios-safari-e2e-single test
  ```

- Run parallel end-to-end tests using Safari mobile browser
  ```
  mvn -P ios-safari-e2e-parallel test
  ```

- Run a single end-to-end test using Chrome mobile browser
  ```
  mvn -P ios-chrome-e2e-single test
  ```

- Run parallel end-to-end tests using Chrome mobile browser
  ```
  mvn -P ios-chrome-e2e-parallel test
  ```

## Using Gradle

### Setup

- Clone the repo
- Install dependencies
  ```
  ./gradlew build
  ```
- Update the environment variables with your [BrowserStack Username and Access Key](https://www.browserstack.com/accounts/settings)

### Running your tests

#### Android

- Run a single end-to-end test using Chrome mobile browser
  ```
  ./gradlew android-chrome-e2e-single
  ```

- Run parallel end-to-end tests using Chrome mobile browser
  ```
  ./gradlew android-chrome-e2e-parallel
  ```

#### iOS

- Run a single end-to-end test using Safari mobile browser
  ```
  ./gradlew ios-safari-e2e-single
  ```

- Run parallel end-to-end tests using Safari mobile browser
  ```
  ./gradlew ios-safari-e2e-parallel
  ```

- Run a single end-to-end test using Chrome mobile browser
  ```
  ./gradlew ios-chrome-e2e-single
  ```

- Run parallel end-to-end tests using Chrome mobile browser
  ```
  ./gradlew ios-chrome-e2e-parallel
  ```

## Notes
- You can view your App-Automate test results on the [BrowserStack App-Automate dashboard](https://app-automate.browserstack.com/).
- Additional details available in [this](https://browserstack.atlassian.net/wiki/spaces/CE/pages/3387883687/Automate+a+Real+E2E+User+Flow+Browser+App) confluence documentation.
- Apps used to test on Android:
    - [Wikipedia Sample App](https://www.browserstack.com/app-automate/sample-apps/android/WikipediaSample.apk)
    - Chrome Mobile App
- Apps used to test on iOS:
    - [BStack Sample App](https://www.browserstack.com/app-automate/sample-apps/ios/BStackSampleApp.ipa)
    - Safari Mobile App
    - Chrome Mobile App
- Export the environment variables for the Username and Access Key of your BrowserStack account.
  ```sh
  export BROWSERSTACK_USERNAME=<browserstack-username> && export BROWSERSTACK_ACCESS_KEY=<browserstack-access-key>
  ```
