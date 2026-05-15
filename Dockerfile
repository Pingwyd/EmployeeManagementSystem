FROM eclipse-temurin:25-jre

WORKDIR /app

# Copy the application JAR into the image. CI will place the JAR as `app.jar` before building.
COPY app.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
