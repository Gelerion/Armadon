package com.gelerion.microservices.licensing.monitor.filter;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

/**
 * This class is used to inject the correlation ID into any outgoing HTTP-based service requests being executed from a
 * RestTemplate instance. This is done to ensure that you can establish a linkage between service calls
 */
public class UserContextInterceptor implements ClientHttpRequestInterceptor {

    //invoked before actual HTTP service call occurs by the RestTemplate
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        HttpHeaders headers = request.getHeaders();

        UserContext current = UserContextHolder.getContext();
        headers.add(UserContext.CORRELATION_ID, current.correlationId());
        headers.add(UserContext.AUTH_TOKEN, current.authToken());

        return execution.execute(request, body);
    }
}
