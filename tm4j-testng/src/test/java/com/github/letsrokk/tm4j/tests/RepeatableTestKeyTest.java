package com.github.letsrokk.tm4j.tests;

import com.github.letsrokk.tm4j.annotation.TestCase;
import org.testng.annotations.Test;
import org.testng.internal.TestResult;

@Test(singleThreaded = true)
public class RepeatableTestKeyTest extends TestBase {

    @TestCase("AQA-T6")
    @Test
    public void sameTestCaseKeyPassTest() {
        tm4jListener.onTestStart(testResult);
        tm4jListener.onTestSuccess(testResult);
    }

    @TestCase("AQA-T6")
    @Test
    public void sameTestCaseKeyFailTest() {
        tm4jListener.onTestStart(testResult);
        testResult.setThrowable(new ExampleException("TM4J Fail Example"));
        testResult.setStatus(TestResult.FAILURE);
        tm4jListener.onTestFailure(testResult);
        testResult.setThrowable(null);
        testResult.setStatus(TestResult.STARTED);
    }

}
