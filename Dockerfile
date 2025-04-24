FROM eclipse-temurin:21-jdk as build
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
COPY src ./src

COPY mvnw pom.xml ./
RUN chmod +x mvnw
RUN ./mvnw package -DskipTests


FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]