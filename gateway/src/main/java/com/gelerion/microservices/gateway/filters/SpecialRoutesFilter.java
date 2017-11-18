package com.gelerion.microservices.gateway.filters;

import com.gelerion.microservices.gateway.model.AbTestingRoute;
import com.gelerion.microservices.gateway.service.RoutesForwarder;
import com.jasongoodwin.monads.Try;
import com.jasongoodwin.monads.TryMapFunction;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.Random;

/**
 * This filter allow you to do A/B testing of a new version of a service
 */
@Slf4j
@Component
public class SpecialRoutesFilter extends ZuulFilter {
    private static final int FILTER_ORDER = 1;

    private final FiltersHelper filtersHelper;
    private final RestTemplate rest;
    private final RoutesForwarder routesForwarder;
    private final DiscoveryClient discoveryClient;

    public SpecialRoutesFilter(FiltersHelper filtersHelper,
                               RestTemplate rest,
                               RoutesForwarder routesForwarder,
                               DiscoveryClient discoveryClient) {
        this.filtersHelper = filtersHelper;
        this.rest = rest;
        this.routesForwarder = routesForwarder;
        this.discoveryClient = discoveryClient;
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
        //cache
        return discoveryClient.getServices().contains("specialroutes");
    }

    @Override
    public Object run() {

        getAbRoutingInfo(filtersHelper.serviceId())
                .filter(this::useSpecialRoute)
                .map(this::buildRouteString)
                .ifPresent(routesForwarder::forwardToSpecialRoute);

        return null;
    }

    //Note: service must exist
    private Optional<AbTestingRoute> getAbRoutingInfo(String serviceName) {
        return Try.ofFailable(() ->
                    rest.getForEntity("http://specialroutes/v1/route/abtesting/{serviceName}",
                            AbTestingRoute.class, serviceName))
                .map(HttpEntity::getBody)
                .recoverWith(nullIfNotFound())
                .onFailure(Throwable::printStackTrace)
                .toOptional();
    }

    private boolean useSpecialRoute(AbTestingRoute testRoute) {
        Random random = new Random();
        if (testRoute.active().equals("N")) return false;

        int value = random.nextInt((10 - 1) + 1) + 1;
        return testRoute.weight() < value;
    }

    private String buildRouteString(AbTestingRoute testingRoute) {
        RequestContext ctx = RequestContext.getCurrentContext();

        String oldEndpoint = ctx.getRequest().getRequestURI();
        String serviceName = filtersHelper.serviceId();
        String newEndpoint = testingRoute.endpoint();

        int index = oldEndpoint.indexOf(serviceName);
        String strippedRoute = oldEndpoint.substring(index + serviceName.length());
        log.info("Target route: {}/{}", newEndpoint, strippedRoute);
        return String.format("%s/%s", newEndpoint, strippedRoute);
    }

    //Pattern matching
    private TryMapFunction<Throwable, Try<AbTestingRoute>> nullIfNotFound() {
        return error -> {
            if (error instanceof HttpClientErrorException) {
                if (((HttpClientErrorException) error).getStatusCode() == HttpStatus.NOT_FOUND)
                    return Try.successful(null);
            }

            throw error;
        };
    }
}
