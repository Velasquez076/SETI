FROM gradle:8.14.0-jdk21 AS build
WORKDIR /app

COPY . .
RUN ls -a
RUN ./gradlew clean build --no-daemon

# ---------- Runtime Stage ----------
FROM eclipse-temurin:21-jdk AS runtime
WORKDIR /app

# Copy only the built JAR from the previous stage
COPY --from=build /app/build/libs/*.jar franchises.jar

# Run the application
ENTRYPOINT ["java", "-jar", "franchises.jar"]