https://speakerdeck.com/coollog/build-containers-faster-with-jib-a-google-image-build-tool-for-java-applications

### Armadon setup  
1. `./gradlew buildImages`  
2. `docker-compose up`

### Endpoints
1.  Obtain the bearer token from the auth server  
    POST http://localhost:8901/oauth/token  
    Use your application name and secret get for basic authentication:
      
    | Base auth     |               | 
    | ------------- |---------------|
    | username      | gelerion      | 
    | password      | thisIsSecret  |
    
    Use the following body (multipart):  
    
    | Name          | Value         | 
    | ------------- |-------------  |
    | username      | john.carnell  |
    | password      | password1     |
    | grant_type    | password      |
    | scope         | webclient     |
    
    You should get the `access_token`, e.g.
    ```
    {
      "access_token": "5849b6ea-127d-48dc-bfc6-c731ef00d5b5",
      "token_type": "bearer",
      "refresh_token": "9520fcb9-7e72-4b42-8620-628ebe74b5d6",
      "expires_in": 43199,
      "scope": "webclient"
    }
    ```
    
2. Get all organizations via the Gateway server (Eureka)
   GET gateway:5555/api/organization/v1/organizations/
   Use the `access_token` for Bearer authorization
   
##### Organizations server API
Use the `access_token` for Bearer authorization

| Name          | method      | url                                             |
| ------------- |-------------|-------------------------------------------------|
| save          | POST        | gateway:5555/api/organization/v1/organizations/ |

Json example:
```
{
	"name": "my-org",
	"contactName": "abc",
	"contactEmail": "abc@email.ff",
	"contactPhone": "1234"
}
```


### Run each service separately
-- config server
-- cd ~/Armadon/config/build/docker
 -- docker build -t config-server-image .
docker run -dit --name config-server -p 8888:8888 --network gelerion-net config-server-image

-- registry-server
-- cd ~/Armadon/registry/build/docker
 -- docker build -t registry-server-image .HttpRequestMethodNotSupportedException
docker run -dit --name registry-server -p 8761:8761 --network gelerion-net registry-server-image

-- gateway server
-- cd ~/Armadon/gateway/build/docker
 -- docker build -t gateway-server-image .
docker run -dit --name gateway-server -p 5555:5555 --network gelerion-net gateway-server-image

-- auth server
-- cd ~/Armadon/auth/build/docker
  -- docker build -t auth-server-image .
docker run -dit --name auth-server -p 8901:8901 --network gelerion-net auth-server-image

docker-compose:
    for windows users: dos2unix wait-for.sh