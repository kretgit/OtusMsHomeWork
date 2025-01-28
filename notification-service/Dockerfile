# Вариант контейнеризации с локальной сборкой

# собираем проект мавеном локально (терминал из корня проекта):
# mvn --projects 'common,notification-service' clean package -DskipTests=true
# далее переходим в папку сервиса: cd notification-service/ и запускаем команды докера

# docker build --platform linux/amd64 -t kretdocker/notification-service:latest .
# docker run -p 8100:8100 kretdocker/notification-service:latest

# docker run -p 8100:8100 --name notification-service -e BROKER_URL='tcp://host.docker.internal:61616' kretdocker/notification-service:latest

# запушить образ в хаб / скачать образ из хаба
# docker push kretdocker/notification-service:latest
# docker image pull kretdocker/notification-service:latest

FROM adoptopenjdk:11-jre-hotspot
ARG TARGET_FILE=target/notification-service*.jar
COPY ${TARGET_FILE} notification-service.jar
EXPOSE 8100
#ENTRYPOINT ["java","-jar","/notification-service.jar","--notification.active-mq.broker-url=tcp://host.docker.internal:61616"]
#ENTRYPOINT ["java","-jar","/notification-service.jar","--notification.active-mq.broker-url=tcp://host.minikube.internal:61616"]
ENTRYPOINT ["java","-jar","/notification-service.jar","--notification.active-mq.broker-url=${BROKER_URL}"]