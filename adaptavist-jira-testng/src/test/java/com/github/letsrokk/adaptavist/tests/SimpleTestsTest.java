package com.github.letsrokk.adaptavist.tests;

import com.github.letsrokk.adaptavist.annotation.TestCase;
import org.testng.annotations.Test;
import org.testng.internal.TestResult;

@Test(singleThreaded = true)
public class SimpleTestsTest extends TestBase {

    @Test
    public void testWithoutAnnotation() {
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

}
