 - Three core client resiliency patterns are the circuit-breaker pattern, the fallback pattern, and the bulkhead pattern.
 - The circuit breaker pattern seeks to kill slow-running and degraded system calls so that the calls
   fail fast and prevent resource exhaustion.
 - The fallback pattern allows you as the developer to define alternative code paths in the event that a remote service
   call fails or the circuit breaker for the call fails.
 - The bulk head pattern segregates remote resource calls away from each other, isolating calls to a remote service
   into their own thread pool. If one set of service calls is failing, its failures shouldn’t be allowed to eat up
   all the resources in the application container.

 - Hystrix’s default isolation model, THREAD, completely isolates a Hystrix protected call, but doesn’t propagate the
   parent thread’s context to the Hystrix managed thread.
 - Hystrix’s other isolation model, SEMAPHORE, doesn’t use a separate thread to make a Hystrix call. While this is more
   efficient, it also exposes the service to unpredictable behavior if Hystrix interrupts the call
