# Вариант контейнеризации с локальной сборкой

# собираем проект мавеном локально (терминал из корня проекта):
# mvn --projects 'common,user-service' clean package -DskipTests=true
# далее переходим в папку сервиса: cd user-service/ и запускаем команды докера

# docker build --platform linux/amd64 -t kretdocker/user-service:latest .
# docker run -p 8000:8000 kretdocker/user-service:latest

# docker run -p 8000:8000 --name user-service -e JDBC_URL='jdbc:postgresql://host.docker.internal:5432/otus_db?currentSchema=user_service' kretdocker/user-service:latest

# запушить образ в хаб / скачать образ из хаба
# docker push kretdocker/user-service:latest
# docker image pull kretdocker/user-service:latest

FROM adoptopenjdk:11-jre-hotspot
ARG TARGET_FILE=target/user-service*.jar
COPY ${TARGET_FILE} user-service.jar
EXPOSE 8000
#ENTRYPOINT ["java","-jar","/user-service.jar","--spring.datasource.url=jdbc:postgresql://host.docker.internal:5432/otus_db?currentSchema=user_service"]
#ENTRYPOINT ["java","-jar","/user-service.jar","--spring.datasource.url=jdbc:postgresql://host.minikube.internal:5432/otus_db?currentSchema=user_service"]
#ENTRYPOINT ["java","-jar","/user-service.jar","--spring.datasource.url=${JDBC_URL}"]
ENTRYPOINT ["java","-jar","/user-service.jar"]