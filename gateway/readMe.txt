A common pattern with a service gateway is to differentiate API routes vs. content routes by prefixing all
your service calls with a type of label such as /api. Zuul supports this by using the prefix attribute
in the Zuul configuration

By default, Zuul will terminate and return an HTTP 500 error for any call that takes longer than one second
to process a request.
Netflix Ribbon also times out any calls that take longer than five seconds.
You can override the Ribbon timeout by setting the following property: servicename.ribbon.ReadTimeout


Pre-filters — A pre-filter is invoked before the actual request to the target destination occurs with Zuul.
    A pre-filter usually carries out the task of making sure that the service has a consistent message format
    (key HTTP headers are in place, for example) or acts as a gatekeeper to ensure that the user calling the service
    is authenticated (they are who they say they are) and authorized (they can do what they’re requesting to do).
Post filters — A post filter is invoked after the target service has been invoked and a response is being sent
    back to the client. Usually a post filter will be implemented to log the response back from the target service,
    handle errors, or audit the response for sensitive information.
Route filters — The route filter is used to intercept the call before the target service is invoked. Usually a
    route filter is used to determine if some level of dynamic routing needs to take place.
    May be used to route between two different versions of the same service so that a small percentage of calls to
    a service are routed to a new version of a service rather than the existing service. This will allow you to
    expose a small number of users to new functionality without having everyone use the new service.