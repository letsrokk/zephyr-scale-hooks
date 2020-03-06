# adaptavist-hooks [ ![Download](https://api.bintray.com/packages/letsrokk/github/adaptavist-jira-parent/images/download.svg) ](https://bintray.com/letsrokk/github/adaptavist-jira-parent/_latestVersion)

Adapter for test execution results exports to Adaptavist Test Management for Jira Server.  
API documentation: [Test Management for Jira Server API (v1)](https://docs.adaptavist.io/tm4j/server/api/v1/)

# TestNG 6.x and 7.x

`testng.xml`

```xml
<!DOCTYPE suite SYSTEM "http://testng.org/testng-1.0.dtd" >
<suite name="Adaptavist TM4J Test Suite" verbose="1" >

    <parameter name="tm4jProjectKey" value="AQA"/>
    
    <listeners>
        <listener class-name="com.github.letsrokk.adaptavist.testng.AdaptavistTestResultListerner"/>
    </listeners>
    
    <test name="Example Test" >
        <classes>
            <class name="com.github.letsrokk.adaptavist.tests.ConfigurationExampleTest" />
        </classes>
    </test>

</suite>
```

# Adaptavist Test Case ID annotations for tests

Test methods should be either annotated by `@TestCase("PROJECT-T1")` or `@TmsLink("PROJECT-T1")` 
(provided by Allure Framework)

```java
package com.github.letsrokk.adaptavist.tests;

import com.github.letsrokk.adaptavist.annotation.TestCase;
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
            <TM4J_JIRA_URL>https://jira.example.org</ZAPI_JIRA_URL>
            <TM4J_JIRA_USERNAME>jirausername</ZAPI_JIRA_USERNAME>
            <TM4J_JIRA_PASSWORD>jirapassword</ZAPI_JIRA_PASSWORD>
        </systemPropertyVariables>
    </configuration>
</plugin>
```
- or as Environment variables
```
export TM4J_JIRA_URL=https://jira.example.com/
export TM4J_JIRA_USERNAME=jirausername
export TM4J_JIRA_PASSWORD=jirapassword
```

# Selecting Jira Project Version for Test Cycle

TBD
 
# Examples

![Test Cycle screen](readme/executions.png?raw=true "Test Cycles Screen")
