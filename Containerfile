###Build Image###
FROM maven:3.8-openjdk-11-slim as builder
COPY . /opt/app
WORKDIR /opt/app
RUN mvn clean package

###Executable Image###
FROM adoptopenjdk/openjdk11:alpine-jre
LABEL maintainer="Naing Ye Minn"
RUN mkdir -p /opt/app
COPY --from=builder /opt/app/target/oom-simulator-0.0.1-SNAPSHOT.jar /opt/app/app.jar
CMD ["sh", "-c", "java $JAVA_OPTS -jar /opt/app/app.jar"]
