package com.github.letsrokk.zephyr.tests;

import com.github.letsrokk.zephyr.annotation.TestCase;
import org.testng.annotations.Test;
import org.testng.internal.TestResult;

@Test(singleThreaded = true)
public class SimpleTestsTest extends TestBase {

    @Test
    public void executionWithoutAnnotationTest() {
        zephyrScaleListener.onTestStart(testResult);
        zephyrScaleListener.onTestSuccess(testResult);
    }

    @TestCase("AQA-T2683")
    @Test
    public void testExecutionWithPassStatusTest() {
        zephyrScaleListener.onTestStart(testResult);
        zephyrScaleListener.onTestSuccess(testResult);
    }

    @TestCase("AQA-T2684")
    @Test
    public void testExecutionWithFailStatusTest() {
        zephyrScaleListener.onTestStart(testResult);
        testResult.setThrowable(new ExampleException("Zephyr Scale Fail Example"));
        testResult.setStatus(TestResult.FAILURE);
        zephyrScaleListener.onTestFailure(testResult);
        testResult.setThrowable(null);
        testResult.setStatus(TestResult.STARTED);
    }

    @TestCase("AQA-T2685")
    @Test
    public void sameTestCaseKeyPassTest() {
        zephyrScaleListener.onTestStart(testResult);
        zephyrScaleListener.onTestSuccess(testResult);
    }

    @TestCase("AQA-T2685")
    @Test
    public void sameTestCaseKeyFailTest() {
        zephyrScaleListener.onTestStart(testResult);
        testResult.setThrowable(new ExampleException("Zephyr Scale Fail Example"));
        testResult.setStatus(TestResult.FAILURE);
        zephyrScaleListener.onTestFailure(testResult);
        testResult.setThrowable(null);
        testResult.setStatus(TestResult.STARTED);
    }

    @TestCase("AQA-T2686")
    @Test
    public void executionWithRetryTest() throws ExampleException {
        zephyrScaleListener.onTestStart(testResult);

        // skip for retry
        testResult.setThrowable(new ExampleException("Fail for retry!"));
        testResult.setStatus(TestResult.SKIP);
        zephyrScaleListener.onTestSkipped(testResult);

        // restore test result parameters
        testResult.setThrowable(null);
        testResult.setStatus(TestResult.STARTED);

        // retry with success
        zephyrScaleListener.onTestSuccess(testResult);
    }
}
