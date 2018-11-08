https://speakerdeck.com/coollog/build-containers-faster-with-jib-a-google-image-build-tool-for-java-applications

Armadon setup
1. prepareDocker (gradle)
2. docket build -t TAG .
3. docker network create --driver bridge gelerion-net
4. docker run -dit --name NAME-server --network gelerion-net IMAGE


-- config server
-- cd D:/Java/Learning/Armadon/config/build/docker
 -- docker build -t config-server-image .
docker run -dit --name config-server -p 8888:8888 --network gelerion-net config-server-image

-- registry-server
-- cd D:/Java/Learning/Armadon/registry/build/docker
 -- docker build -t registry-server-image .
docker run -dit --name registry-server -p 8761:8761 --network gelerion-net registry-server-image

-- gateway server
-- cd D:/Java/Learning/Armadon/gateway/build/docker
 -- docker build -t gateway-server-image .
docker run -dit --name gateway-server -p 5555:5555 --network gelerion-net gateway-server-image

-- auth server
-- cd D:/Java/Learning/Armadon/auth/build/docker
  -- docker build -t auth-server-image .
docker run -dit --name auth-server -p 8901:8901 --network gelerion-net auth-server-image