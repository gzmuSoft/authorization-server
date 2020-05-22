FROM gradle:5.4.1-jdk11 AS build
LABEL version="1.0.0-SNAPSHOT" description="授权服务器" by="EchoCow"
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle bootjar

FROM openjdk:11-jre-slim
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/ /app/
EXPOSE 8888

ENTRYPOINT ["java","-jar","/app/authorization-server.jar", "--spring.profiles.active=prod"]
