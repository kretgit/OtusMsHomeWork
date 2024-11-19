# Вариант контейнеризации со сборкой из репозитория (только user-service)

# docker build --platform linux/amd64 -t kretdocker/user-service:latest .
# docker run -p 8000:8000 kretdocker/user-service:latest

# запушить образ в хаб / скачать образ из хаба
# docker push kretdocker/user-service:latest
# docker image pull kretdocker/user-service:latest

FROM maven:3.6.3-openjdk-17 AS build
WORKDIR /otus-application
COPY ./pom.xml .
COPY ./common ./common
COPY ./user-service ./user-service
COPY ./notification ./notification
RUN mvn --projects 'common,user-service' clean package -DskipTests=true

FROM adoptopenjdk:11-jre-hotspot
COPY --from=build /otus-application/user-service/target/user-service*.jar user-service.jar
EXPOSE 8000
ENTRYPOINT ["java","-jar","user-service.jar"]