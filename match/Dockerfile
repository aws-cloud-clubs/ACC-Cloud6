FROM amazoncorretto:21-alpine3.17

WORKDIR /app

ENV SPRING_PROFILES_ACTIVE=prod

COPY build/libs/*-SNAPSHOT.jar app.jar


EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
