package com.github.letsrokk.zephyr.client;

import com.github.letsrokk.zephyr.client.request.CreateTestExecutionRequest;
import com.github.letsrokk.zephyr.client.model.TestCycle;
import com.github.letsrokk.zephyr.client.request.CreateTestCycleRequest;
import com.github.letsrokk.zephyr.client.response.GetTestCyclesResponse;
import lombok.extern.log4j.Log4j2;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Log4j2
public class ZephyrScaleClient {

    private ZephyrScaleAPIClient apiClient;

    protected ZephyrScaleClient(ZephyrScaleAPIClient apiClient) {
        this.apiClient = apiClient;
    }

    public List<TestCycle> getTestCycles() throws IOException {
        Response<GetTestCyclesResponse> response =
                apiClient.getTestCycles(null, null, null, null).execute();
        return Objects.requireNonNull(response.body()).getValues();
    }

    public TestCycle createTestCycle(String projectKey, String name) {
        CreateTestCycleRequest createTestCycleRequest = CreateTestCycleRequest.builder()
                .name(name)
                .projectKey(projectKey)
                .build();
        try {
            String testCycleKey =
                    Objects.requireNonNull(apiClient.createTestCycle(createTestCycleRequest).execute().body()).getKey();
            return getTestCycleByKey(testCycleKey);
        } catch (IOException e) {
            log.throwing(e);
            return null;
        }
    }

    public TestCycle getTestCycleByKey(String testCycleKeyOrId) {
        try {
            return apiClient.getTestCycle(testCycleKeyOrId).execute().body();
        } catch (IOException e) {
            log.throwing(e);
            return null;
        }
    }

    public Optional<TestCycle> getTestCycleByProjectKeyAndName(String projectKey, String testRunName) {
        try {
            List<TestCycle> testCycles = new ArrayList<>();
            int startAt = 0;
            int maxResults = 50;
            boolean isLast = false;
            while (!isLast) {
                GetTestCyclesResponse response =
                        Objects.requireNonNull(apiClient.getTestCycles(projectKey, null, maxResults, startAt).execute().body());
                testCycles.addAll(Optional.ofNullable(response.getValues()).orElse(Collections.emptyList()));
                isLast = response.getIsLast();
                if (!isLast) {
                    startAt += maxResults;
                }
            }
            return Objects.requireNonNull(testCycles).stream()
                    .filter(t -> t.getKey().matches("([A-Za-z]+)-R([0-9]+)")
                        && t.getName().equalsIgnoreCase(testRunName))
                    .findFirst();
        } catch (IOException e) {
            log.throwing(e);
            return Optional.empty();
        }
    }

    public void createTestExecution(CreateTestExecutionRequest execution) {
        try {
            apiClient.createTestExecution(execution).execute();
        } catch (IOException e) {
            log.throwing(e);
        }
    }

}
