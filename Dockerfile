FROM  maven:3.8.1-openjdk-11-slim AS BUILD
WORKDIR /usr/app/
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:11-slim
WORKDIR /usr/app/
COPY --from=BUILD /usr/app/target/shopping-service-0.0.1-SNAPSHOT.jar shopping-service-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar","shopping-service-0.0.1-SNAPSHOT.jar"]
