FROM tomcat:9.0.48-jdk11-openjdk-slim
LABEL author="yurkevichvv"
COPY ./build/libs/com.qulix.yurkevichvv.trainingtask.wicket-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/wicket.war
CMD ["/usr/local/tomcat/bin/catalina.sh", "run"]

