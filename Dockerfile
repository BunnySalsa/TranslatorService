FROM maven:3.6-openjdk-17-slim AS translator

ADD pom.xml /
RUN mvn verify clean
ADD . /
RUN mvn package

FROM openjdk:17
WORKDIR /app
EXPOSE 8081
ENV API-KEY ${API-KEY}
COPY --from=0 /target/*-jar-with-dependencies.jar .
ENTRYPOINT ["java", "-jar","translator-0.0.1-SNAPSHOT-jar-with-dependencies.jar", "-port=8081"]