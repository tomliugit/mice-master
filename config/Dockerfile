FROM java:latest
VOLUME /tmp
ADD ./target/config.jar /app.jar
RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
  && echo 'Asia/Shanghai' >/etc/timezone \
RUN bash -c 'touch /app.jar'
EXPOSE 6502
ENTRYPOINT ["java","-Xms256m","-Xmx256m","-jar","/app.jar"]