FROM 10.10.13.30:9090/alpha-openjdk/jre8-rasp-skywalking:1.6
COPY target/alpha-fc.jar /alpha-fc.jar
USER root
RUN cd /
ENV JAVA_OPTS="-javaagent:/skywalking-agent/skywalking-agent.jar -Dfile.encoding=UTF8 -Djava.security.egd=file:/dev/./urandom -Duser.timezone=Asia/Shanghai"
ENTRYPOINT [ "sh", "-c", "java  $JAVA_OPTS -jar alpha-fc.jar" ]
EXPOSE 8085