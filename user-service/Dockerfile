# docker build --platform linux/amd64 -t kretdocker/user-service:latest .
# docker run -p 8000:8000 kretdocker/user-service:latest
# docker push kretdocker/user-service:latest
# docker image pull kretdocker/user-service:latest
FROM adoptopenjdk:11-jre-hotspot
COPY target/user-service-1.0.0.jar user-service-1.0.0.jar
EXPOSE 8000
ENTRYPOINT ["java","-jar","/user-service-1.0.0.jar"]