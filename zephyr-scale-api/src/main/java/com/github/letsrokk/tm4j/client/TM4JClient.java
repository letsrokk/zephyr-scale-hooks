package com.github.letsrokk.tm4j.client;

import com.github.letsrokk.tm4j.client.model.Execution;
import com.github.letsrokk.tm4j.client.model.TestRun;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Log4j2
public class TM4JClient {

    private TM4JAPIClient apiClient;

    protected TM4JClient(TM4JAPIClient apiClient) {
        this.apiClient = apiClient;
    }

    public TestRun createTestRun(String projectKey, String name) {
        TestRun testRun = TestRun.builder()
                .name(name)
                .projectKey(projectKey)
                .build();
        try {
            String testRunKey =
                    apiClient.createTestRun(testRun).execute().body().getKey();
            testRun =
                    getTestRunByKey(testRunKey);
            return testRun;
        } catch (IOException e) {
            log.throwing(e);
            return null;
        }
    }

    public TestRun getTestRunByKey(String testRunKey) {
        try {
            return apiClient.getTestRun(testRunKey).execute().body();
        } catch (IOException e) {
            log.throwing(e);
            return null;
        }
    }

    public Optional<TestRun> getTestRunByProjectKeyAndName(String projectKey, String testRunName) {
        try {
            String searchByProjectKeyQuery = String.format("projectKey = \"%s\"", projectKey);
            List<TestRun> testRuns = apiClient.searchTestRun(searchByProjectKeyQuery).execute().body();
            return Objects.requireNonNull(testRuns).stream()
                    .filter(t -> t.getProjectKey().equalsIgnoreCase(projectKey)
                            && t.getName().equalsIgnoreCase(testRunName))
                    .findFirst();
        } catch (IOException e) {
            log.throwing(e);
            return Optional.empty();
        }
    }

    public void postResult(Execution execution) {
        try {
            apiClient.postExecution(execution).execute();
        } catch (IOException e) {
            log.throwing(e);
        }
    }

    public void postResult(TestRun testRun, List<Execution> executions) {
        postResult(testRun.getKey(), executions);
    }

    public void postResult(String testRunKey, List<Execution> executions) {
        try {
            apiClient.postExecutionsForTestRun(testRunKey, executions).execute();
        } catch (IOException e) {
            log.throwing(e);
        }
    }

}
