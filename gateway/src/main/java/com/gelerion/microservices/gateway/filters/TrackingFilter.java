package com.gelerion.microservices.gateway.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;


@Slf4j
@Component
public class TrackingFilter extends ZuulFilter {
    private static final int     FILTER_ORDER = 1;
    private static final boolean SHOULD_FILTER = true;

    private final FiltersHelper filtersHelper;

    public TrackingFilter(FiltersHelper filtersHelper) {
        this.filtersHelper = filtersHelper;
    }

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return SHOULD_FILTER;
    }

    @Override
    public Object run() {
        Optional<String> correlationId = filtersHelper.correlationId();
        if (correlationId.isPresent()) {
            log.debug("{} found in tracking filter {}", FiltersHelper.CORRELATION_ID, correlationId.get());
        }
        else {
            String generatedCorrelationId = generateCorrelationId();
            filtersHelper.correlationId(generatedCorrelationId);
            log.debug("{} generated in tracking filter {}", FiltersHelper.CORRELATION_ID, generatedCorrelationId);
        }

        RequestContext ctx = RequestContext.getCurrentContext();
        log.debug("Processing incoming request for {}",  ctx.getRequest().getRequestURI());
        return null;
    }

    private String generateCorrelationId(){
        return UUID.randomUUID().toString();
    }
}
