FROM gradle:jdk11
LABEL authors="yurkevichvv"
WORKDIR /usr/local/hsqldb

COPY sql-scripts ./sql-scripts
COPY build.gradle .

RUN gradle wrapper
RUN ./gradlew build
RUN ./gradlew startDatabase

CMD ["bash"]