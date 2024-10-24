# docker build --platform linux/amd64 -t kretdocker/user-service:latest .
# docker run -p 8000:8000 kretdocker/user-service:latest
# docker push kretdocker/user-service:latest
# sha256:1ac51660465a851c8c46851fffd4b5279cffb54341d5f425619e88ed2d6fee81
# docker image pull kretdocker/user-service:latest
FROM adoptopenjdk:11-jre-hotspot
COPY target/user-service-1.0.0.jar user-service-1.0.0.jar
EXPOSE 8000
ENTRYPOINT ["java","-jar","/user-service-1.0.0.jar"]