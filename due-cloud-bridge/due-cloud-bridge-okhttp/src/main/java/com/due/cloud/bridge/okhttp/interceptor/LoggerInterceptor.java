package com.due.cloud.bridge.okhttp.interceptor;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

@Slf4j
public class LoggerInterceptor implements Interceptor {
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();
        long start = System.nanoTime();
        log.info(String.format("Sending request %s on %s%n%s",
                request.url(), chain.connection(), request.headers()));
        Response response = chain.proceed(request);
        long end = System.nanoTime();
        log.info(String.format("Received response for %s in %.1fms%n%s",
                response.request().url(), (end - start) / 1e6d, response.headers()));
        return response;
    }
}
