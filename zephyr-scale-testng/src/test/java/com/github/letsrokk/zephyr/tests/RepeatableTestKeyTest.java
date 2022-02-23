package com.github.letsrokk.zephyr.tests;

import com.github.letsrokk.zephyr.annotation.TestCase;
import org.testng.annotations.Test;
import org.testng.internal.TestResult;

@Test(singleThreaded = true)
public class RepeatableTestKeyTest extends TestBase {

    @TestCase("AQA-T2686")
    @Test
    public void sameTestCaseKeyPassTest() {
        zephyrScaleListener.onTestStart(testResult);
        zephyrScaleListener.onTestSuccess(testResult);
    }

    @TestCase("AQA-T2686")
    @Test
    public void sameTestCaseKeyFailTest() {
        zephyrScaleListener.onTestStart(testResult);
        testResult.setThrowable(new ExampleException("Zephyr Scale Fail Example"));
        testResult.setStatus(TestResult.FAILURE);
        zephyrScaleListener.onTestFailure(testResult);
        testResult.setThrowable(null);
        testResult.setStatus(TestResult.STARTED);
    }

}
