# docker build --platform linux/amd64 -t kretdocker/user-service:latest .
# docker run -p 8000:8000 kretdocker/user-service:latest
# docker push kretdocker/user-service:latest
# docker image pull kretdocker/user-service:latest
FROM adoptopenjdk:11-jre-hotspot
ARG TARGET_FILE=target/user-service*.jar
COPY ${TARGET_FILE} user-service.jar
EXPOSE 8000
ENTRYPOINT ["java","-jar","/user-service.jar"]