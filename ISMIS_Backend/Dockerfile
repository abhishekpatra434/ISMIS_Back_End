From tomcat:8.0.51-jre8-alpine
RUN rm -rf /usr/local/tomcat/webapps/*
COPY /target/ISMISB.war /usr/local/tomcat/webapps/ISMISB.war
CMD ["catalina.sh","run"]