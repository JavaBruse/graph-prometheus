FROM openjdk:17-jdk-slim
LABEL authors="javabruse"
WORKDIR /app
COPY target/graph-prometheus-1.0.jar app.jar
EXPOSE 80
ENTRYPOINT ["java","-jar","app.jar"]
