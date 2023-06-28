
FROM openjdk:11
WORKDIR /app
COPY ./target/TraineeDetails-1.0.0.jar /app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "TraineeDetails-1.0.0.jar"]
