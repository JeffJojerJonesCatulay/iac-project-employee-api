# Build Stage
FROM public.ecr.aws/amazoncorretto/amazoncorretto:21 AS build

RUN yum install -y maven

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src

RUN mvn clean package -DskipTests

# Runtime Stage
FROM public.ecr.aws/amazoncorretto/amazoncorretto:21

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 9010

USER 1000

ENTRYPOINT ["java","-jar","app.jar"]