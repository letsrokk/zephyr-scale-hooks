package com.github.letsrokk.tm4j.tests;

import com.github.letsrokk.tm4j.annotation.TestCase;
import org.testng.annotations.Test;
import org.testng.internal.TestResult;

@Test(singleThreaded = true)
public class SimpleTestsTest extends TestBase {

    @Test
    public void executionWithoutAnnotationTest() {
        tm4jListener.onTestStart(testResult);
        tm4jListener.onTestSuccess(testResult);
    }

    @TestCase("AQA-T2683")
    @Test
    public void testExecutionWithPassStatusTest() {
        tm4jListener.onTestStart(testResult);
        tm4jListener.onTestSuccess(testResult);
    }

    @TestCase("AQA-T2684")
    @Test
    public void testExecutionWithFailStatusTest() {
        tm4jListener.onTestStart(testResult);
        testResult.setThrowable(new ExampleException("TM4J Fail Example"));
        testResult.setStatus(TestResult.FAILURE);
        tm4jListener.onTestFailure(testResult);
        testResult.setThrowable(null);
        testResult.setStatus(TestResult.STARTED);
    }

    @TestCase("AQA-T2685")
    @Test
    public void sameTestCaseKeyPassTest() {
        tm4jListener.onTestStart(testResult);
        tm4jListener.onTestSuccess(testResult);
    }

    @TestCase("AQA-T2685")
    @Test
    public void sameTestCaseKeyFailTest() {
        tm4jListener.onTestStart(testResult);
        testResult.setThrowable(new ExampleException("TM4J Fail Example"));
        testResult.setStatus(TestResult.FAILURE);
        tm4jListener.onTestFailure(testResult);
        testResult.setThrowable(null);
        testResult.setStatus(TestResult.STARTED);
    }

    @TestCase("AQA-T2686")
    @Test
    public void executionWithRetryTest() throws ExampleException {
        tm4jListener.onTestStart(testResult);

        // skip for retry
        testResult.setThrowable(new ExampleException("Fail for retry!"));
        testResult.setStatus(TestResult.SKIP);
        tm4jListener.onTestSkipped(testResult);

        // restore test result parameters
        testResult.setThrowable(null);
        testResult.setStatus(TestResult.STARTED);

        // retry with success
        tm4jListener.onTestSuccess(testResult);
    }
}
