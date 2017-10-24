UI Test Framework
=================

## Build test
- To build jar/zip file to delivery test artifact
	```
	mvn clean install -DskipTests
	```
  The structure of zip artifact:
    - ghost-ui-tests.jar: executable jar file
    - libs: dependency libraries
    - testdata: test data folder, files may json, images...
    - log4j2.xml
    - configuration.properties

- To test by maven (Same as [REAMDME.md](REAMDME.md))
	```
	mvn clean verify
	```


## Run test
Execute a command:
  ```
  java -Dconfigfile=local-configuration.properties -Dprofile=smoke -jar ghost-ui-tests.jar
  ```
  - `profile`: Test profile (`smoke`|`regression`|`full`) or whatever tjat correspoding to group test in TESTNG
  - `configfile`: Specify the configuration file to run test. Default: `configuration.properties`

*Note*: 
  - Take a look at `configuration.properties` for more details
  - Using configuration key in `configuration.properties` as system properties to overwrite parameters. 
  
## Test Output
- Default report test output is `target` folder that same directory level of test jar artifact. It's full report that used in development.
- `logs` is log folder when executing test.
- `reports` includes TestNG report.

## Development process:
- Install `TestNG` plugin
- Clone `configuration.properties` to `local-configuration.properties` and fill appropriate values
- Two way run on Eclipse:
  - Configure `Debug as../Run as..` on `TestRunner.java` in whichs `VM arguments` may has optional appropriate value in configuration file.
  - Run directly on each test case in `com.zero.selenium.tests`