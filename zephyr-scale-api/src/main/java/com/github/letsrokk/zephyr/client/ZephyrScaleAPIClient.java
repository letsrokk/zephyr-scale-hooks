package com.github.letsrokk.zephyr.client;

import com.github.letsrokk.zephyr.client.request.CreateTestExecutionRequest;
import com.github.letsrokk.zephyr.client.model.TestCycle;
import com.github.letsrokk.zephyr.client.request.CreateTestCycleRequest;
import com.github.letsrokk.zephyr.client.response.CreateTestCyclesResponse;
import com.github.letsrokk.zephyr.client.response.GetTestCyclesResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

interface ZephyrScaleAPIClient {

    @Headers("Content-Type: application/json")
    @GET("testcycles")
    Call<GetTestCyclesResponse> getTestCycles(@Query("projectKey") String projectKey,
                                              @Query("folderId") Integer folderId,
                                              @Query("maxResults") Integer maxResults,
                                              @Query("startAt") Integer startAt);

    @Headers("Content-Type: application/json")
    @GET("testcycles/{testCycleKeyOrId}")
    Call<TestCycle> getTestCycle(@Path("testCycleKeyOrId") String testCycleKeyOrId);

    @Headers("Content-Type: application/json")
    @POST("testcycles")
    Call<CreateTestCyclesResponse> createTestCycle(@Body CreateTestCycleRequest body);

    @Headers("Content-Type: application/json")
    @POST("testexecutions")
    Call<CreateTestExecutionRequest> createTestExecution(@Body CreateTestExecutionRequest execution);

}
