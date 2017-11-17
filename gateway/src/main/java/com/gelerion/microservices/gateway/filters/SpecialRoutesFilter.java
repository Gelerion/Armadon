package com.gelerion.microservices.gateway.filters;

import com.netflix.zuul.ZuulFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * This filter allow you to do A/B testing of a new version of a service
 */
@Component
public class SpecialRoutesFilter extends ZuulFilter {
    private static final int FILTER_ORDER = 1;
    private static final boolean SHOULD_FILTER = true;

    private final FiltersHelper filterUtils;

    private final RestTemplate restTemplate;

    @Autowired
    public SpecialRoutesFilter(FiltersHelper filterUtils, RestTemplate restTemplate) {
        this.filterUtils = filterUtils;
        this.restTemplate = restTemplate;
    }

    @Override
    public String filterType() {
        return FilterConstants.ROUTE_TYPE;
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
        return null;
    }
}
