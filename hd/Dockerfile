FROM openjdk:21-jdk-slim
ARG JAR_FILE=hd/target/*.jar
COPY ${JAR_FILE} app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java", "-Djava.security.egd=file:", "-jar","/app.jar"]