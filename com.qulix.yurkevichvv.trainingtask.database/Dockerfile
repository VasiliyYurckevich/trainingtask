FROM openjdk:11
LABEL authors="yurkevichvv"
WORKDIR /usr/local/hsqldb

COPY sql-scripts sql-scripts
COPY build.gradle .

RUN \
    cd /usr/local && \
    curl -L https://services.gradle.org/distributions/gradle-5.4.1-bin.zip -o gradle-5.4.1-bin.zip && \
    unzip gradle-5.4.1-bin.zip && \
    rm gradle-5.4.1-bin.zip

ENV GRADLE_HOME=/usr/local/gradle-5.4.1
ENV PATH=$PATH:$GRADLE_HOME/bin

RUN ["gradle","wrapper"]
RUN ["./gradlew", "startDatabase"]