FROM maven:3.8-openjdk-17-slim AS build

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Gán quyền thực thi cho mvnw
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline

COPY src ./src
COPY .env.docker .env


RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "app.jar"]
