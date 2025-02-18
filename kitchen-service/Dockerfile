# Вариант контейнеризации с локальной сборкой

# собираем проект мавеном локально (терминал из корня проекта):
# mvn --projects 'common,kitchen-service' clean package -DskipTests=true
# далее переходим в папку сервиса: cd kitchen-service/ и запускаем команды докера

# docker build -t kretdocker/kitchen-service:latest .
# docker run -p 8200:8200 kretdocker/kitchen-service:latest

# docker run -p 8200:8200 --name kitchen-service -e BROKER_URL='tcp://host.docker.internal:61616' -e ORDER_SERVICE_URL='http://host.docker.internal:8300/order' kretdocker/kitchen-service:latest

# запушить образ в хаб / скачать образ из хаба
# docker push kretdocker/kitchen-service:latest
# docker image pull kretdocker/kitchen-service:latest

FROM adoptopenjdk:11-jre-hotspot
ARG TARGET_FILE=target/kitchen-service*.jar
COPY ${TARGET_FILE} kitchen-service.jar
EXPOSE 8200
#ENTRYPOINT ["java","-jar","/kitchen-service.jar","--kitchen.active-mq.broker-url=tcp://host.docker.internal:61616", "--kitchen.order-service-url=http://host.docker.internal:8300/order"]
#ENTRYPOINT ["java","-jar","/kitchen-service.jar","--kitchen.active-mq.broker-url=tcp://host.minikube.internal:61616", "--kitchen.order-service-url=http://host.minikube.internal:8300/order"]
ENTRYPOINT ["java","-jar","/kitchen-service.jar","--kitchen.active-mq.broker-url=${BROKER_URL}", "--kitchen.order-service-url=${ORDER_SERVICE_URL}"]