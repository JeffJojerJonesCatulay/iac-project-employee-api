# Install Maven
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
COPY pom.xml .
RUN mvn dependency:go-offline
RUN mvn clean package -DskipTests

# Run JDK
FROM public.ecr.aws/amazoncorretto/amazoncorretto:21-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar emp-api.jar
EXPOSE 9010
ENTRYPOINT ["java", "-jar", "emp-api.jar"]