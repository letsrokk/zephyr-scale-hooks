package com.github.letsrokk.adaptavist.tests;

import com.github.letsrokk.adaptavist.annotation.TestCase;
import org.testng.annotations.Test;
import org.testng.internal.TestResult;

@Test(singleThreaded = true)
public class SimpleTestsTest extends TestBase {

    @Test
    public void executionWithoutAnnotationTest() {
        tm4jListener.onTestStart(testResult);
        tm4jListener.onTestSuccess(testResult);
    }

    @TestCase("AQA-T2")
    @Test
    public void testExecutionWithPassStatusTest() {
        tm4jListener.onTestStart(testResult);
        tm4jListener.onTestSuccess(testResult);
    }

    @TestCase("AQA-T3")
    @Test
    public void testExecutionWithFailStatusTest() {
        tm4jListener.onTestStart(testResult);
        testResult.setThrowable(new ExampleException("Adaptavist Fail Example"));
        testResult.setStatus(TestResult.FAILURE);
        tm4jListener.onTestFailure(testResult);
        testResult.setThrowable(null);
        testResult.setStatus(TestResult.STARTED);
    }

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
        testResult.setThrowable(new ExampleException("Adaptavist Fail Example"));
        testResult.setStatus(TestResult.FAILURE);
        tm4jListener.onTestFailure(testResult);
        testResult.setThrowable(null);
        testResult.setStatus(TestResult.STARTED);
    }

    @TestCase("AQA-T9")
    @Test
    public void executionWithRetryTest() throws ExampleException {
        tm4jListener.onTestStart(testResult);

        // skip for retry
        testResult.setThrowable(new ExampleException("Fail for retry!"));
        testResult.setStatus(TestResult.SKIP);
        testResult.setWasRetried(true);
        tm4jListener.onTestSkipped(testResult);

        // restore test result parameters
        testResult.setThrowable(null);
        testResult.setStatus(TestResult.STARTED);
        testResult.setWasRetried(false);

        // retry with success
        tm4jListener.onTestSuccess(testResult);
    }
}
