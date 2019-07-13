# Prerequisites
1. In hierarchy always exist only One root
2. No loops
3. Before save new hierarchy we clear the whole database

# Build application
```
./gradlew bootJar
```

# Start Docker (database)
```
docker-compose -f docker/docker-compose.yml up
```

# Start Application
```
java -jar ./build/libs/personio_challenge.jar
```

# Make requests
## Security
To invoke any endpoint please pass **Basic Authentication**

login: **admin**  
password: **admin**

## Save hierarchy of the company
**POST** request on endpoint **http://localhost:8080/saveHierarchy**
example body
```
{
    "Pete": "Nick",
    "Barbara": "Nick",
    "Nick": "Sophie",
    "Sophie": "Jonas"
}
```

## Get supervisors for Pete
**GET** request on endpoint **http://localhost:8080/getHierarchy?name=Pete**

## Run tests
```
./gradlew test
```
