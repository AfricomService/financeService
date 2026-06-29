FROM eclipse-temurin:17-jdk

EXPOSE 8888

ADD target/finance-service-0.0.1-SNAPSHOT.jar application.jar

ENTRYPOINT ["java", "-jar", "/application.jar"]
