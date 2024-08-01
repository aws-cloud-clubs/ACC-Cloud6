FROM amazoncorretto:21-alpine3.17

WORKDIR /app

ENV SPRING_PROFILES_ACTIVE=prod

COPY build/libs/*-SNAPSHOT.jar app.jar

# Redis CLI 설치
RUN apk add --no-cache redis

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
