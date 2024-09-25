FROM amazoncorretto:17.0.7-alpine

LABEL maintainer 'SmartMES'

WORKDIR /opt

# Install Dependecies
RUN apk update && apk add tzdata curl maven

# Timezone Brail UTC -3
RUN cp /usr/share/zoneinfo/Brazil/East /etc/localtime
RUN echo "Brazil/East" >  /etc/timezone

COPY ./ /opt/project

RUN cd /opt/project && mvn clean install -U -DskipTests

RUN mv /opt/project/target/*.jar /opt/app.jar

ARG APP_PORT=8080

CMD java $JAVA_OPTS -jar app.jar

EXPOSE $APP_PORT