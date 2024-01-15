FROM eclipse-temurin:17-jdk-focal
 
WORKDIR /chalk-be
COPY .mvn/ .mvn
COPY pom.xml ./
COPY mvnw ./
RUN apt-get update && \
    apt-get install dos2unix
RUN dos2unix mvnw
RUN ./mvnw dependency:go-offline


COPY src ./src
 
CMD ["./mvnw", "spring-boot:run","-Dspring-boot.run.arguments='seed'"]
