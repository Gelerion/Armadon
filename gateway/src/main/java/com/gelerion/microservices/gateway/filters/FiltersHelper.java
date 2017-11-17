package com.gelerion.microservices.gateway.filters;

import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.util.Optional.ofNullable;

@Component
public class FiltersHelper {
    static final String CORRELATION_ID = "tmx-correlation-id";
    static final String AUTH_TOKEN     = "tmx-auth-token";
    static final String USER_ID        = "tmx-user-id";
    static final String ORG_ID         = "tmx-org-id";

    Optional<String> correlationId() {
        RequestContext context = RequestContext.getCurrentContext();

        return ofNullable(
                 ofNullable(context.getRequest().getHeader(CORRELATION_ID))
                .orElseGet(() -> context.getZuulRequestHeaders().get(CORRELATION_ID))
        );

    }

    FiltersHelper correlationId(String correlationId) {
        RequestContext context = RequestContext.getCurrentContext();
        context.addZuulRequestHeader(CORRELATION_ID, correlationId);
        return this;
    }

    String serviceId() {
        RequestContext ctx = RequestContext.getCurrentContext();

        //We might not have a service id if we are using a static, non-eureka route.
        if (ctx.get("serviceId")==null) return "";
        return ctx.get("serviceId").toString();
    }
}
