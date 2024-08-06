FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/transactionAuthorization.jar /app/transactionAuthorization.jar
EXPOSE 6666
ENTRYPOINT ["java", "-jar", "/app/transactionAuthorization.jar"]
