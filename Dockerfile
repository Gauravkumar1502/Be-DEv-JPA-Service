FROM maven:3.8.7-openjdk-18-slim AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-slim
COPY --from=build /target/*.jar /BeDev.jar
EXPOSE 8080
CMD ["java", "-jar", "-Dspring.profiles.active=prod", "-DskipTests", "-Dserver.port=8080", "/BeDev.jar"]