FROM gradle:jdk17 as build
WORKDIR /app
COPY . .
RUN gradle clean build

FROM openjdk:17-slim
WORKDIR /app
COPY --from=build /app/build/libs/chatmq-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
CMD ["java", "-jar", "chatmq-0.0.1-SNAPSHOT.jar"]
