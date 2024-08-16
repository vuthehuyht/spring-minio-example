FROM eclipse-temurin:17-jdk-alpine AS builder

WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN apk add dos2unix  \
    && chmod +x -R ./mvnw && dos2unix mvnw  \
    && ./mvnw dependency:go-offline
COPY ./src ./src

RUN ./mvnw clean install -DskipTests

FROM eclipse-temurin:17-jre-alpine

# Create spring group and add user java to spring group
RUN addgroup -S spring; adduser -S java -G spring
USER java

WORKDIR /angiday
EXPOSE 8080
COPY --from=builder /app/target/*.jar backend.jar
ENTRYPOINT ["sh", "-c", "java -jar backend.jar"]
