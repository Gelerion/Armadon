package com.gelerion.microservices.organization.monitor.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@Slf4j
public class UserContextFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        UserContextHolder.getContext()
                .correlationId(httpRequest.getHeader(UserContext.CORRELATION_ID))
                .userId(httpRequest.getHeader(UserContext.USER_ID))
                .authToken(httpRequest.getHeader(UserContext.AUTH_TOKEN))
                .orgId(httpRequest.getHeader(UserContext.ORG_ID));

        log.debug("UserContextFilter Correlation id: {}", UserContextHolder.getContext().correlationId());

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
