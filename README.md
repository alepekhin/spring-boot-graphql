# spring-boot-graphql

Demo usage GraphQL with Spring Boot and WebFlux.
First we built GraphQL client and get data from GraphQL fake server,
see https://graphqlzero.almansi.me/
Then we pass the data through our GraphQL server.
We retrieve a list of Users and one User by ID as an example.

Project was built with Spring Boot initializer with minimum dependencies.

## How to build locally

Java 17 is required.

```
./gradlew clean build
```

Test result and coverage can be found at `build/reports`

## How to run locally

```
java -jar build/libs/demo-0.0.1-SNAPSHOT.jar
```

## How to test locally

After running the application Postman can be used for testing API.
In the latest version it has nice integration with GraphQL and schema.
API URL is
```
http://localhost:8080/graphql
```

## Other project features

There much more other features than just GraphQL
Try them after staring the application and executing some queries.

### Get health
```
http://localhost:8080/actuator/health
{"status":"UP"}
```
### Logging and exposing current git version
```
http://localhost:8080/actuator/info
will show current app version:
{"git":{"branch":"main","commit":{"id":"7bfb738","time":"2023-08-03T13:44:07Z"}}}
```

### Get metrics
Show current request execution time
```
http://localhost:8080/actuator/metrics/get_user_time
{"name":"get_user_time","baseUnit":"seconds","measurements":[{"statistic":"COUNT","value":1.0},{"statistic":"TOTAL_TIME","value":1.262898466},{"statistic":"MAX","value":0.0}],"availableTags":[{"tag":"exception","values":["none"]},{"tag":"method","values":["user"]},{"tag":"class","values":["com.example.demo.feature.user.UserQueryResolver"]}]}
```
### Get prometheus metrics
```
http://localhost:8080/actuator/prometheus
incuding current request execution time
# HELP get_user_time_seconds_max  
# TYPE get_user_time_seconds_max gauge
get_user_time_seconds_max{class="com.example.demo.feature.user.UserQueryResolver",exception="none",method="user",} 0.0
# HELP get_user_time_seconds  
# TYPE get_user_time_seconds summary
get_user_time_seconds_count{class="com.example.demo.feature.user.UserQueryResolver",exception="none",method="user",} 1.0
get_user_time_seconds_sum{class="com.example.demo.feature.user.UserQueryResolver",exception="none",method="user",} 1.262898466
```
### Write logs compatible with Kibana
```
"%d{yyyy-MM-dd'T'HH:mm:ss.SSXXX} [%p] %msg%n"
```



