package com.github.letsrokk.adaptavist.testng;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.letsrokk.adaptavist.annotation.TestCase;
import com.github.letsrokk.adaptavist.client.TM4JClient;
import com.github.letsrokk.adaptavist.client.TM4JClientFactory;
import com.github.letsrokk.adaptavist.client.model.Execution;
import com.github.letsrokk.adaptavist.client.model.ExecutionStatus;
import com.github.letsrokk.adaptavist.client.model.TestRun;
import com.github.letsrokk.adaptavist.testng.container.CustomExecutionContainer;
import com.github.letsrokk.adaptavist.testng.container.CustomExecutionException;
import com.github.letsrokk.adaptavist.testng.container.CustomSuiteContainer;
import com.github.letsrokk.adaptavist.testng.container.CustomTestContainer;
import io.qameta.allure.TmsLink;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.internal.ConstructorOrMethod;
import org.testng.xml.XmlTest;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
public class AdaptavistTestResultListerner implements ISuiteListener, ITestListener {

    private static TM4JClient tm4jClient;
    private static CustomSuiteContainer suiteContainer;

    @Getter
    @AllArgsConstructor
    private enum AnnotationType {
        TestCase(com.github.letsrokk.adaptavist.annotation.TestCase.class.getCanonicalName()),
        TmsLink(io.qameta.allure.TmsLink.class.getCanonicalName()),
        Unsupported("Unsupported");

        private String canonicalName;

        public static AnnotationType getAnnotationType(Annotation annotation) {
            return Arrays.stream(AnnotationType.values())
                    .filter(t -> {
                        String annotationName = annotation.annotationType().getCanonicalName();
                        return t.getCanonicalName().equals(annotationName);
                    })
                    .findFirst()
                    .orElse(AnnotationType.Unsupported);
        }

        public static boolean isSupported(Annotation annotation) {
            return getAnnotationType(annotation) != Unsupported;
        }
    }

    private void initTm4jClient() {
        tm4jClient = TM4JClientFactory.builder().build();
    }

    private void initSuiteContainer(String projectKey, String suiteName) {
        if (projectKey != null && !projectKey.equals("")) {
            suiteContainer = CustomSuiteContainer.builder()
                    .projectKey(projectKey)
                    .name(suiteName)
                    .build();

            TestRun testRun = tm4jClient.createTestRun(suiteContainer.getProjectKey(), suiteContainer.getName());
            suiteContainer.setTestRunKey(testRun.getKey());
        } else {
            log.error("TM4J Project Key is not set");
            suiteContainer = CustomSuiteContainer.builder()
                    .name(suiteName)
                    .build();
        }
    }

    @Override
    public void onStart(ISuite suite) {
        initTm4jClient();
        String tm4jProjectKey = suite.getParameter("tm4jProjectKey");
        String tm4jTestRunName = suite.getName();
        initSuiteContainer(tm4jProjectKey, tm4jTestRunName);
    }

    @Override
    public void onFinish(ISuite suite) {
        // no actions needed
        try {
            String suiteContainerJson = new ObjectMapper()
                    .registerModule(new JavaTimeModule())
                    .writerWithDefaultPrettyPrinter().writeValueAsString(suiteContainer);
            log.debug(suiteContainerJson);
        } catch (JsonProcessingException e) {
            log.throwing(e);
        }
    }

    @Override
    public void onStart(ITestContext testContext) {
        CustomTestContainer testContainer = CustomTestContainer.builder()
                .source(calculateTestContainerSource(testContext.getCurrentXmlTest()))
                .build();
        suiteContainer.getTests().add(testContainer);
    }

    @Override
    public void onFinish(ITestContext testContext) {
        postResults(testContext.getCurrentXmlTest());
    }

    @Override
    public void onTestStart(ITestResult testResult) {
        createExecutionContainer(testResult);
    }

    @Override
    public void onTestSuccess(ITestResult testResult) {
        updateCustomExecutionStatus(testResult, ExecutionStatus.PASS);
    }

    @Override
    public void onTestFailure(ITestResult testResult) {
        updateCustomExecutionStatus(testResult, ExecutionStatus.FAIL);
    }

    @Override
    public void onTestSkipped(ITestResult testResult) {
        updateCustomExecutionStatus(testResult, ExecutionStatus.NOT_EXECUTED);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult testResult) {
        updateCustomExecutionStatus(testResult, ExecutionStatus.FAIL);
    }

    private String calculateTestContainerSource(XmlTest xmlTest) {
        String name = xmlTest.getName();
        String toString = xmlTest.toString();
        return String.format("%s@%s", name, toString);
    }

    private Optional<CustomTestContainer> getCustomTestContainer(ITestResult testResult) {
        return getCustomTestContainer(testResult.getMethod().getXmlTest());
    }

    private Optional<CustomTestContainer> getCustomTestContainer(XmlTest xmlTest) {
        return suiteContainer.getTests().stream()
                .filter(tc -> tc.getSource().equals(calculateTestContainerSource(xmlTest)))
                .findFirst();
    }

    private String calculateTestCaseKey(ITestResult testResult) {
        ITestNGMethod iTestNGMethod = testResult.getMethod();
        ConstructorOrMethod constructorOrMethod = iTestNGMethod.getConstructorOrMethod();
        Method method = constructorOrMethod.getMethod();
        Annotation[] declaredAnnotations = method.getDeclaredAnnotations();

        Optional<Annotation> annotation =
                Arrays.stream(declaredAnnotations)
                        .filter(AnnotationType::isSupported)
                        .findFirst();
        String testCaseKey = null;
        if (annotation.isPresent()) {
            switch (AnnotationType.getAnnotationType(annotation.get())) {
                case TestCase:
                    testCaseKey = ((TestCase) annotation.get()).value();
                    break;
                case TmsLink:
                    testCaseKey = ((TmsLink) annotation.get()).value();
                    break;
                case Unsupported:
                default:
                    testCaseKey = "";
            }
        }
        return testCaseKey;
    }

    private String calculateExecutionSource(ITestResult testResult) {
        String qualifiedName = testResult.getMethod().getQualifiedName();
        return String.format("%s", qualifiedName);
    }

    private Optional<CustomExecutionContainer> getCustomExecutionContainer(ITestResult testResult) {
        Optional<CustomTestContainer> testContainer = getCustomTestContainer(testResult);
        if (testContainer.isPresent()) {
            String testCaseKey = calculateTestCaseKey(testResult);
            String source = calculateExecutionSource(testResult);

            return testContainer.get().getExecutions().stream()
                    .filter(e -> testCaseKey != null
                            && !testCaseKey.equals("")
                            && testCaseKey.equals(e.getTestCaseKey())
                            && source != null
                            && source.equals(e.getSource()))
                    .findFirst();
        } else {
            return Optional.empty();
        }
    }

    synchronized private void createExecutionContainer(ITestResult testResult) {
        Optional<CustomExecutionContainer> executionContainer = getCustomExecutionContainer(testResult);

        if (!executionContainer.isPresent()) {
            String testCaseKey = calculateTestCaseKey(testResult);
            String executionSource = calculateExecutionSource(testResult);

            if (testCaseKey != null && !testCaseKey.equals("")) {
                CustomExecutionContainer newExecutionContainer =
                        CustomExecutionContainer.builder()
                                .testCaseKey(testCaseKey)
                                .source(executionSource)
                                .result(ExecutionStatus.NOT_EXECUTED)
                                .startDate(LocalDateTime.now(ZoneOffset.UTC))
                                .build();

                getCustomTestContainer(testResult).ifPresent(tc -> tc.getExecutions().add(newExecutionContainer));
            }
        }
    }

    synchronized private void updateCustomExecutionStatus(ITestResult testResult, String status) {
        Optional<CustomExecutionContainer> executionContainer = getCustomExecutionContainer(testResult);

        executionContainer.ifPresent(e -> {
            if (e.getResult() == null
                    || e.getResult().equals(ExecutionStatus.NOT_EXECUTED)
                    || status.equals(ExecutionStatus.FAIL)
            ) {
                e.setResult(status);
            }
            updateExceptions(e, testResult);
            e.setEndDate(LocalDateTime.now(ZoneOffset.UTC));
        });
    }

    private void updateExceptions(CustomExecutionContainer execution, ITestResult testResult) {
        Throwable throwable = testResult.getThrowable();
        if (Objects.nonNull(throwable)) {
            List<Object> parameters = Arrays.asList(testResult.getParameters());
            String exceptionMessage = Optional.ofNullable(throwable.getMessage()).orElse(throwable.getClass().getName());
            CustomExecutionException exception = CustomExecutionException.builder()
                    .parameters(parameters)
                    .exceptionMessage(exceptionMessage)
                    .build();

            execution.getExceptions().add(exception);
        }
    }

    private void postResults(XmlTest xmlTest) {
        if (suiteContainer.getProjectKey() != null) {
            Optional<CustomTestContainer> testContainer = getCustomTestContainer(xmlTest);
            testContainer.ifPresent(tc -> {
                List<Execution> executions = tc.getExecutions().stream()
                        .filter(ec -> ec.getTestCaseKey() != null && !ec.getTestCaseKey().equals(""))
                        .map(ec -> {
                            Execution execution = Execution.builder()
                                    .testCaseKey(ec.getTestCaseKey())
                                    .plannedStartDate(ec.getStartDate())
                                    .actualStartDate(ec.getStartDate())
                                    .plannedEndDate(ec.getEndDate())
                                    .actualEndDate(ec.getEndDate())
                                    .status(ec.getResult())
                                    .build();

                            long executionTime =
                                    Duration.between(execution.getActualStartDate(), execution.getActualEndDate()).toMillis();
                            execution.setExecutionTime(executionTime);

                            if (!ec.getExceptions().isEmpty()) {
                                try {
                                    String exceptionComment = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(ec.getExceptions());
                                    execution.setComment(exceptionComment);
                                } catch (JsonProcessingException e) {
                                    log.error("Error serializing Comment field", e);
                                }
                            }
                            return execution;
                        }).collect(Collectors.toList());
                if (!executions.isEmpty()) {
                    tm4jClient.postResult(suiteContainer.getTestRunKey(), executions);
                }
            });
        }
    }

}
