FROM maven:3.9.6-openjdk-17-slim AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-slim
COPY --from=build /target/*.jar /app.jar
EXPOSE 8080
CMD ["java", "-jar", "/app.jar"]