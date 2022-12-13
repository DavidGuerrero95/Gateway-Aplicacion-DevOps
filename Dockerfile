## mvn --version
FROM maven:3.6.3-openjdk-11 AS build
WORKDIR /home/app
COPY . /home/app
RUN mvn -f /home/app/pom.xml clean package -DskipTests

## java --version
FROM openjdk:12
VOLUME /tmp
EXPOSE 8090
ARG JAR_FILE=target/*.jar
COPY --from=build /home/app/target/*.jar app.jar
ENTRYPOINT ["sh", "-c", "java -jar /app.jar"]