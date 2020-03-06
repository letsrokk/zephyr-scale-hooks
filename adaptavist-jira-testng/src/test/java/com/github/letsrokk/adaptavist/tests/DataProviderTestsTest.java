package com.github.letsrokk.adaptavist.tests;

import com.github.letsrokk.adaptavist.annotation.TestCase;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.internal.TestResult;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Test(singleThreaded = true)
public class DataProviderTestsTest extends TestBase {

    @DataProvider
    private Iterator<Object[]> simpleDataProvider() {
        List<Object[]> cases = new ArrayList<>();

        cases.add(new Object[]{true});
        cases.add(new Object[]{false});

        return cases.iterator();
    }

    @TestCase("AQA-T4")
    @Test(dataProvider = "simpleDataProvider")
    public void testExecutionDataProviderWithSuccessTest(boolean success) {
        tm4jListener.onTestStart(testResult);
        tm4jListener.onTestSuccess(testResult);
    }

    @TestCase("AQA-T5")
    @Test(dataProvider = "simpleDataProvider")
    public void testExecutionDataProviderWithFailTest(boolean success) {
        tm4jListener.onTestStart(testResult);

        if(success) {
            tm4jListener.onTestSuccess(testResult);
        } else {
            testResult.setThrowable(new ExampleException("Adaptavist Fail Example"));
            testResult.setStatus(TestResult.FAILURE);
            tm4jListener.onTestFailure(testResult);
            testResult.setThrowable(null);
            testResult.setStatus(TestResult.STARTED);
        }
    }

    private static class ExampleException extends Exception {
        public ExampleException(String message) {
            super(message);
        }
    }

    @DataProvider(parallel = true)
    private Iterator<Object[]> parallelDataProvider() {
        List<Object[]> cases = new ArrayList<>();

        cases.add(new Object[]{true});
        cases.add(new Object[]{false});

        return cases.iterator();
    }

    @TestCase("AQA-T7")
    @Test(dataProvider = "parallelDataProvider")
    public void textExecutionWithParallelDataProviderTest(boolean success) {
        tm4jListener.onTestStart(testResult);
        tm4jListener.onTestSuccess(testResult);
    }
}
