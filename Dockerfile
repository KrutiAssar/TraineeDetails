FROM maven:4.0.0-jdk-8 as builder
WORKDIR /app
COPY . .
RUN mvn dependency:resolve
RUN mvn clean install -DskipTests

FROM openjdk:17
WORKDIR /app
COPY --from=builder /app/target/TraineeDetails-1.0.0.jar .
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "TraineeDetails-1.0.0.jar"]
