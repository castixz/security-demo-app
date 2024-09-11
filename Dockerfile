FROM ibm-semeru-runtimes:open-21-jdk AS build
WORKDIR /app
COPY . .
RUN ./gradlew build

FROM ibm-semeru-runtimes:open-21-jre
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
