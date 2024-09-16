#Use a Maven image to build the application
FROM maven:3.8.1-openjdk-17-slim AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

#Use OpenJDK 17 for runtime
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar /app/app.jar

#Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
