FROM tomcat:9.0.48-jdk11-openjdk-slim
LABEL author="yurkevichvv"
COPY ./build/libs/com.qulix.yurkevichvv.trainingtask.servlets-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/servlet.war
CMD ["/usr/local/tomcat/bin/catalina.sh", "run"]

