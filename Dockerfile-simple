FROM openjdk:11-jre-slim
LABEL version="1.0.0-SNAPSHOT" description="授权服务器" by="EchoCow"
RUN mkdir /app
COPY build/libs/authorization-server.jar /app/
EXPOSE 8888

ENTRYPOINT ["java","-jar","/app/authorization-server.jar", "--spring.profiles.active=prod"]
