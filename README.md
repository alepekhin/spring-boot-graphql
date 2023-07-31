# spring-boot-graphql

Demo usage GraphQL with Spring Boot.
First we built GraphQL client and get data from GraphQL fake server.
Then we pass the data through our GraphQL server.

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
