FROM amazoncorretto:17-alpine
ARG JAR_FILE=/target/spring-boot-security-jwt-demo-0.0.1.jar
COPY ${JAR_FILE} /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 9090