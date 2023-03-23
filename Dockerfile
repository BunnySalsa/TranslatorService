FROM maven:3.6-openjdk-17-slim AS translator

ADD pom.xml /
RUN mvn verify clean
ADD . /
RUN mvn package

FROM openjdk:17
WORKDIR /root/
EXPOSE 8081
ENV OAuth-token y0_AgAAAABXco2tAATuwQAAAADfFp0PXf4JVyp5Q4O1FZVuT6ooVZqHjkg
ENV folderId b1gkd141l14iovkfjeif
COPY --from=0 /target/*-jar-with-dependencies.jar translator-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar","./translator-0.0.1-SNAPSHOT.jar", "-port=8081"]