FROM maven:3.9.9 AS builder
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package

FROM openjdk:17
COPY --from=builder /usr/src/app/target/demo-0.0.1-SNAPSHOT.jar /usr/app/demo-0.0.1-SNAPSHOT.jar  
EXPOSE 8080  
CMD ["java","-jar","/usr/app/demo-0.0.1-SNAPSHOT.jar"]
