# syntax=docker/dockerfile:1

### ── Build stage ─────────────────────────────────────────────
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Copy the Gradle wrapper + build scripts first so dependencies are
# cached in a separate layer and only re-downloaded when they change.
COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./
RUN chmod +x gradlew && ./gradlew dependencies --no-daemon || true

# Copy sources and build the executable (fat) jar.
COPY src src
RUN ./gradlew bootJar --no-daemon

### ── Runtime stage ───────────────────────────────────────────
FROM eclipse-temurin:21-jre AS runtime
WORKDIR /app

# Run as an unprivileged user.
RUN groupadd --system spring && useradd --system --gid spring spring
USER spring:spring

COPY --from=build /app/build/libs/EduCore-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
