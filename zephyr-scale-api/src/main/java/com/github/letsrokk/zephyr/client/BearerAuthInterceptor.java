package com.github.letsrokk.zephyr.client;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

class BearerAuthInterceptor implements Interceptor {

    private String token;

    public BearerAuthInterceptor(String token) {
        this.token = token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request.Builder requestBuilder = original.newBuilder()
                .header("Authorization", String.format("Bearer %s", token));
        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}

