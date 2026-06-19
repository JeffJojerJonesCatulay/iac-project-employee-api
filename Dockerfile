# Build
FROM public.ecr.aws/amazoncorretto/amazoncorretto:21 AS build
RUN yum install -y maven
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

# Run
FROM public.ecr.aws/amazoncorretto/amazoncorretto:21
WORKDIR /app
COPY --from=build /app/target/*.jar emp-api.jar
EXPOSE 9010
ENTRYPOINT ["java", "-jar", "emp-api.jar"]