FROM openjdk:17
VOLUME /tmp
WORKDIR /app
COPY fitlife-app/target/fitlife-app-1.0.0.jar /app
EXPOSE 8080
CMD ["java", "-jar", "fitlife-app-1.0.0.jar"]