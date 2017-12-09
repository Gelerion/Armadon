OAuth2Utils

Authenticating the user
At this point you have enough of your base OAuth2 server functionality in place to perform application and user
authentication for the password grant flow. Now you’ll simulate a user acquiring an OAuth2 token by using POSTMAN
to POST to the http://localhost:8901/auth/oauth/token endpoint and provide the application, secret key, user ID,
and password.
1 - Basic Authentication with credential configured in ClientDetailsServiceConfigurer
2 - Body [form-data]
    grant_type — The OAuth2 grant type you’re executing. [ClientDetailsServiceConfigurer.authorizedGrantTypes]
    scope — The applications scope. Because you only defined two legitimate scopes when you registered the application
            (webclient and mobileclient) the value passed in must be one of these two scopes.
    username — Name of the user logging in. [WebSecurityConfigurer]
    password — Password of the user logging in. [WebSecurityConfigurer]

The payload returned contains five attributes:
access_token—The OAuth2 token that will be presented with each service call the user makes to a protected resource.
token_type—The type of token. The OAuth2 specification allows you to define multiple token types. The most common token
           type used is the bearer token. We won’t cover any of the other token types in this chapter.
refresh_token—Contains a token that can be presented back to the OAuth2 server to reissue a token after it has been expired.
expires_in—This is the number of seconds before the OAuth2 access token expires. The default value for authorization
           token expiration in Spring is 12 hours.
Scope—The scope that this OAuth2 token is valid for