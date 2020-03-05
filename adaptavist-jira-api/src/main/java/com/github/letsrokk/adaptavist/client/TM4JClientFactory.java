package com.github.letsrokk.adaptavist.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.concurrent.TimeUnit;

public class TM4JClientFactory {

    final private String tm4jPath = "rest/atm/1.0/";

    private String jiraBaseUrl;
    private String username;
    private String password;

    private TM4JClientFactory(
            String jiraBaseUrl, String username, String password
    ) {
        this.jiraBaseUrl = jiraBaseUrl;
        this.username = username;
        this.password = password;
    }

    public static TM4JClientFactory builder() {
        return new TM4JClientFactory(
                getEnvProperty("TM4J_JIRA_URL"),
                getEnvProperty("TM4J_JIRA_USERNAME"),
                getEnvProperty("TM4J_JIRA_PASSWORD")
        );
    }

    private static String getEnvProperty(String name) {
        return System.getProperty(name, System.getenv(name));
    }

    public TM4JClient build() {
        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        BasicAuthInterceptor authInterceptor = new BasicAuthInterceptor(this.username, this.password);

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(authInterceptor)
                .addInterceptor(loggingInterceptor)
                .build();

        String tm4jBaseUrl =
                this.jiraBaseUrl.endsWith("/")
                        ? this.jiraBaseUrl + tm4jPath
                        : this.jiraBaseUrl + "/" + tm4jPath;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(tm4jBaseUrl)
                .client(client)
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .build();

        TM4JAPIClient apiClient = retrofit.create(TM4JAPIClient.class);

        return new TM4JClient(apiClient);
    }

}
