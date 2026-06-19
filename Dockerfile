FROM public.ecr.aws/amazoncorretto/amazoncorretto:21 AS build

RUN yum install -y tar

WORKDIR /app

COPY . .

RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

FROM public.ecr.aws/amazoncorretto/amazoncorretto:21

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 9010

ENTRYPOINT ["java","-jar","app.jar"]