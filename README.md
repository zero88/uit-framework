UI Test Framework
=======================

A Maven template for Selenium 3 that has the latest dependencies so that you can just check out and start writing tests in three easy steps.

### Prerequisites

1. [JDK 8](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
2. [Maven 3](https://maven.apache.org/download.cgi)
3. [Chrome 59](https://www.google.com/chrome/browser/desktop/index.html)
4. [Firefox 52](https://www.mozilla.org/en-US/firefox/new/)

### How to run it

1. Open a terminal window/command prompt
2. `cd framework` (Or whatever folder you stored it into)
3. `mvn clean verify`

All dependencies should now be downloaded and the example Selenium test will have run successfully (assuming you have Firefox installed in the default location).

### What should I know?

- To run any unit tests that test your Selenium framework you just need to ensure that all unit test file names end, or start with "test" and they will be run as part of the build.
- The maven failsafe plugin has been used to create a profile with the id "selenium-tests".  This is active by default. It will pick up any files that **end in** `XO` by default.

You **must specify** a URL parameter for your tests when running from the terminal:
```
-Dsite.url=http://foo.com
```

Yes, you can specify which browser to use by using one of the following switches:

- -Dbrowser=firefox
- -Dbrowser=chrome

You don't need to worry about downloading the chromedriver or geckodriver binaries, this project will do that for you automatically.

You are expected to work with the following pairs:
```
ChromeDriver 2.29.461571 / Google Chrome 59.0.3071.115
geckodriver 0.16.1 / Mozilla Firefox 52.2.0
```

### It's not working!!!

You have probably got outdated driver binaries, by default they are not overwritten if they already exist to speed things up.  You have two options:

- `mvn clean verify -Doverwrite.binaries=true`
- Delete the `selenium_standalone_binaries` folder in your resources directory
