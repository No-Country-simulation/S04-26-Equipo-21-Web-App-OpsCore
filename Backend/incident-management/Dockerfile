FROM eclipse-temurin:17.0.19_10-jre-alpine-3.23
WORKDIR /app
ENV JAVA_OPTS="-Xms256m -Xmx512m"
COPY target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Dspring.profiles.active=prod -jar app.jar"]
# docker build --platform=linux/amd64 -t eduardomaravill/incident-management:latest .
