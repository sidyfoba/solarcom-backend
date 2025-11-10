# ===== Build stage =====
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
# download deps first (better layer caching)
RUN mvn -q -e -DskipTests dependency:go-offline

COPY src ./src
RUN mvn -q -e -DskipTests package

# ===== Runtime stage =====
FROM eclipse-temurin:21-jre
WORKDIR /app
# Copy fat jar (adjust the jar name if you don't use spring-boot default)
COPY --from=build /app/target/*.jar /app/app.jar

# Optional: expose Spring health actuator for healthcheck (already in compose curl)
EXPOSE 9000
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
