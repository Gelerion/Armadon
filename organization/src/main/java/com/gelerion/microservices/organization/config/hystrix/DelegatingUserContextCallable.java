package com.gelerion.microservices.organization.config.hystrix;


import com.gelerion.microservices.organization.monitor.filter.UserContext;
import com.gelerion.microservices.organization.monitor.filter.UserContextHolder;

import java.util.concurrent.Callable;

public final class DelegatingUserContextCallable<V> implements Callable<V> {
    private final Callable<V> delegate;
    private UserContext originalUserContext;

    public static <V> Callable<V> create(Callable<V> delegate,
                                         UserContext userContext) {
        return new DelegatingUserContextCallable<>(delegate, userContext);
    }

    DelegatingUserContextCallable(Callable<V> delegate,
                                  UserContext userContext) {
        this.delegate = delegate;
        this.originalUserContext = userContext;
    }

    @Override
    public V call() throws Exception {
        UserContextHolder.setContext(originalUserContext);

        try {
            return delegate.call();
        }
        finally {
            this.originalUserContext = null;
        }
    }
}
