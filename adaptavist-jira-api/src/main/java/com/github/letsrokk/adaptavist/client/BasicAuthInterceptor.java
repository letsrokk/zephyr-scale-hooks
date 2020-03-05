package com.github.letsrokk.adaptavist.client;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

class BasicAuthInterceptor implements Interceptor {

    private String credential;

    public BasicAuthInterceptor(String username, String password) {
        this.credential = Credentials.basic(username, password);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request.Builder requestBuilder = original.newBuilder()
                .header("Authorization", this.credential);
        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}

