FROM eclipse-temurin:21-jdk-jammy

# copy jar file into container
COPY target/service-reg-0.0.1-SNAPSHOT.jar app.jar

# run application
ENTRYPOINT ["java","-jar","/app.jar"]