Populate api-portal-server with few sample APIs. Useful for developers during development

# Prerequisites
* [Java](http://www.oracle.com/technetwork/java/javase/downloads/index.html) 8 or above
* [Maven](http://maven.apache.org/) 3 or above


# Usage
```
java -jar seeder-1.0-SNAPSHOT.jar
```
By default seeder modules tries to connect to api-collab-server instance at 8080 port on localhost. These defaults can be overridden as below
```
java -jar seeder-1.0-SNAPSHOT.jar --api.portal.host=<jost> --api.portal.port=<port>
```