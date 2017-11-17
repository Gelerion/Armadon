package com.gelerion.microservices.gateway.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.gelerion.microservices.gateway.filters.FiltersHelper.CORRELATION_ID;

@Slf4j
@Component
public class ResponseFilter extends ZuulFilter {
    private static final int FILTER_ORDER = 1;
    private static final boolean SHOULD_FILTER = true;

    private final FiltersHelper filtersHelper;

    public ResponseFilter(FiltersHelper filtersHelper) {
        this.filtersHelper = filtersHelper;
    }

    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
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
        RequestContext ctx = RequestContext.getCurrentContext();

        filtersHelper.correlationId().ifPresent(correlationId -> {
            log.debug("Adding the correlation id to the outbound headers. {}", correlationId);
            ctx.getResponse().addHeader(CORRELATION_ID, correlationId);
            log.debug("Completing outgoing request for {}.", ctx.getRequest().getRequestURI());
        });

        return null;
    }
}
