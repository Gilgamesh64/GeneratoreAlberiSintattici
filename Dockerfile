# ==========================================
# STAGE 1: BUILD
# ==========================================

FROM eclipse-temurin:23-jdk AS builder

WORKDIR /app

# Copy Gradle wrapper and build configuration
COPY gradlew .
COPY gradle ./gradle
COPY settings.gradle .
COPY build.gradle .

# Make Gradle wrapper executable
RUN chmod +x gradlew

# Copy source code
COPY src ./src
COPY public ./public

# Build the application distribution
RUN ./gradlew --no-daemon installDist


# ==========================================
# STAGE 2: RUNTIME
# ==========================================

FROM eclipse-temurin:23-jre

WORKDIR /app

# Copy only the generated application distribution
COPY --from=builder /app/build/install/syntaxtree/ /app/
COPY --from=builder /app/public /app/public

# Expose the backend port
EXPOSE 8080

# Start the application
ENTRYPOINT ["/app/bin/syntaxtree"]