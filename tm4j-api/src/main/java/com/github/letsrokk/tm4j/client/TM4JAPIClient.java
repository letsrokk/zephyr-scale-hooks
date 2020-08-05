package com.github.letsrokk.tm4j.client;

import com.github.letsrokk.tm4j.client.model.Execution;
import com.github.letsrokk.tm4j.client.model.TestRun;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

import java.util.List;

interface TM4JAPIClient {

    @Headers("Content-Type: application/json")
    @POST("testrun")
    Call<TestRun> createTestRun(@Body TestRun body);

    @Headers("Content-Type: application/json")
    @GET("testrun/{testRunKey}")
    Call<TestRun> getTestRun(@Path("testRunKey") String testRunKey);

    @Headers("Content-Type: application/json")
    @POST("testrun/{testRunKey}/testresults")
    Call<List<Execution>> postExecutionsForTestRun(@Path("testRunKey") String testRunKey, @Body List<Execution> executions);

    @Headers("Content-Type: application/json")
    @POST("testresult")
    Call<Execution> postExecution(Execution execution);

    @Multipart
    @POST("automation/execution/{projectKey}")
    Call<Execution> automation(@Path("projectKey") String projectKey, @Part("file") RequestBody zip);
}
