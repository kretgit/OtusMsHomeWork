# Вариант контейнеризации с локальной сборкой

# собираем проект мавеном локально (терминал из корня проекта):
# mvn --projects 'common,order-service' clean package -DskipTests=true
# далее переходим в папку сервиса: cd order-service/ и запускаем команды докера

# docker build -t kretdocker/order-service:latest .
# docker run -p 8300:8300 kretdocker/order-service:latest

# docker run -p 8300:8300 --name order-service -e BROKER_URL='tcp://host.docker.internal:61616' -e JDBC_URL='jdbc:postgresql://host.docker.internal:5432/otus_db?currentSchema=order_service' kretdocker/order-service:latest

# запушить образ в хаб / скачать образ из хаба
# docker push kretdocker/order-service:latest
# docker image pull kretdocker/order-service:latest

FROM adoptopenjdk:11-jre-hotspot
ARG TARGET_FILE=target/order-service*.jar
COPY ${TARGET_FILE} order-service.jar
EXPOSE 8300
#ENTRYPOINT ["java","-jar","/order-service.jar","--order.active-mq.broker-url=tcp://host.docker.internal:61616", "--spring.datasource.url=jdbc:postgresql://host.docker.internal:5432/otus_db?currentSchema=order_service"]
#ENTRYPOINT ["java","-jar","/order-service.jar","--order.active-mq.broker-url=tcp://host.minikube.internal:61616", "--spring.datasource.url=jdbc:postgresql://host.minikube.internal:5432/otus_db?currentSchema=order_service"]
ENTRYPOINT ["java","-jar","/order-service.jar","--order.active-mq.broker-url=${BROKER_URL}", "--spring.datasource.url=${JDBC_URL}"]