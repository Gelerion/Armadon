Hystrix:
There are four client resiliency patterns:
 - Client-side load balancing
 - Circuit breakers [ensures that a client does not calling repeatedly calling failing service]
 - Fallbacks [when a call does fail, fallback asks if there is an alternative]
 - Bulkheads [segregates different service calls to ensure a poor-behaving services does not use all the resources]