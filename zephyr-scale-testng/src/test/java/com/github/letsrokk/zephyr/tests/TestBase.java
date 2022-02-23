package com.github.letsrokk.zephyr.tests;

import com.github.letsrokk.zephyr.testng.ZephyrScaleListener;
import org.testng.ISuite;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import java.util.Collections;

public class TestBase {

    final static protected String TM4J_PROJECT_KEY = "AQA";
    final static protected String TESTNG_PARAMETER_TM4J_PROJECT_KEY = "ZEPHYR_SCALE_PROJECT_KEY";
    static protected ZephyrScaleListener tm4jListener;
    static protected ISuite testSuite;
    static protected ITestContext testContext;
    static protected ITestResult testResult;

    @BeforeSuite
    public void startSuite(ITestContext iTestContext) {
        testSuite = iTestContext.getSuite();
        testSuite.getXmlSuite().setParameters(Collections.singletonMap(TESTNG_PARAMETER_TM4J_PROJECT_KEY, TM4J_PROJECT_KEY));
        tm4jListener = new ZephyrScaleListener();
        tm4jListener.onStart(testSuite);
    }

    @AfterSuite
    public  void finishSuite(ITestContext iTestContext) {
        tm4jListener.onFinish(testSuite);
    }

    @BeforeTest
    public void startTest(ITestContext iTestContext) {
        testContext = iTestContext;
        tm4jListener.onStart(testContext);
    }

    @AfterTest
    public void finishTest(ITestContext iTestContext) {
        tm4jListener.onFinish(testContext);
    }

    @BeforeMethod
    public void initTestResult(ITestResult iTestResult) {
        testResult = iTestResult;
    }

    protected static class ExampleException extends Exception {
        public ExampleException(String message) {
            super(message);
        }
    }

}
