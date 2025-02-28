# Runtime Stage
FROM eclipse-temurin:21-alpine
WORKDIR /app
COPY target/Gemini-bot-0.0.1-SNAPSHOT.jar gemini-bot.jar
CMD ["java", "-jar", "gemini-bot.jar"]
 EXPOSE 8080
