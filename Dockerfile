FROM maven:3.8.3-openjdk-17 AS build
COPY src /app/src
COPY pom.xml /app/pom.xml

RUN mvn -f /app/pom.xml clean install -DskipTests
FROM openjdk:20-ea-17-jdk
COPY --from=build /app/target/lookup*.jar /usr/local/bin/app.jar
ENTRYPOINT ["java","-jar","/usr/local/bin/app.jar"]