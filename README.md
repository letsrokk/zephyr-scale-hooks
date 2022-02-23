# zephyr-scale-hooks

Adapter for test execution results exports to Zephyr Scale for Jira Cloud.  
API documentation: [Zephyr Scale for Jira Cloud (v2)](https://support.smartbear.com/zephyr-scale-cloud/api-docs/)

# TestNG 6.x and 7.x

`testng.xml`

```xml
<!DOCTYPE suite SYSTEM "http://testng.org/testng-1.0.dtd" >
<suite name="Zephyr Scale Test Suite" verbose="1" >

    <parameter name="ZEPHYR_SCALE_PROJECT_KEY" value="AQA"/>
    
    <listeners>
        <listener class-name="com.github.letsrokk.zephyr.testng.ZephyrScaleListener"/>
    </listeners>
    
    <test name="Example Test" >
        <classes>
            <class name="com.github.letsrokk.zephyr.tests.ConfigurationExampleTest" />
        </classes>
    </test>

</suite>
```

# Zephyr Scale Test Case ID annotations for tests

Test methods should be either annotated by `@TestCase("PROJECT-T1")` or `@TmsLink("PROJECT-T1")` 
(provided by Allure Framework)

```java
package com.github.letsrokk.zephyr.tests;

import com.github.letsrokk.zephyr.annotation.TestCase;
import org.testng.annotations.Test;

@Test(singleThreaded = true)
public class ConfigurationExampleTest {

    @TestCase("AQA-T1")
    @Test
    public void testExecutionWithPassStatusTest() {
        // do nothing
    }

}
```

# Parameters

Jira URL, username and token can be set in one of 2 ways:
- as System Property variable on test run
```
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version${maven-surefire-plugin.version}</version>
    <configuration>
        <testFailureIgnore>true</testFailureIgnore>
        <systemPropertyVariables>
            <ZEPHYR_SCALE_API_TOKEN>[REDACTED]</ZAPI_JIRA_URL>
        </systemPropertyVariables>
    </configuration>
</plugin>
```
- or as Environment variables
```
export ZEPHYR_SCALE_API_TOKEN=[REDACTED]
```

# Selecting Jira Project Version for Test Cycle

TBD
 
# Examples

![Test Cycle screen](readme/executions.png?raw=true "Test Cycles Screen")
