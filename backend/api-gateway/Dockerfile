FROM maven:3.9.11-eclipse-temurin-21 AS build
WORKDIR /workspace

COPY backend/pom.xml backend/pom.xml
COPY backend/libs backend/libs
COPY backend/api-gateway backend/api-gateway

RUN mvn -f backend/pom.xml -pl api-gateway -am clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /workspace/backend/api-gateway/target/api-gateway-*.jar app.jar
EXPOSE 9000
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
