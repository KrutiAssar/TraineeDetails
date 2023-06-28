
FROM openjdk:17
WORKDIR /app
COPY --from=builder /app/target/TraineeDetails-1.0.0.jar .
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "TraineeDetails-1.0.0.jar"]
