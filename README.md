# Prerequisites
1. In hierarchy always exist only One root
2. No loops
3. Before save new hierarchy we clear the whole database

# Build application
./gradlew bootJar

# Start Docker (database)
```
docker-compose up -f docker/docker-compose.yml
```

# Start Application
java -jar ./build/libs/personio_challenge.jar

# Make requests
## Save hierarchy of the company
POST request on endpoint http://localhost:8080/saveHierarchy
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
GET request on endpoint http://localhost:8080/getHierarchy?name=Pete
