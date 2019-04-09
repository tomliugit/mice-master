FROM java:latest
VOLUME /tmp
ADD ./target/monitor.jar /app.jar
RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
  && echo 'Asia/Shanghai' >/etc/timezone \
RUN bash -c 'touch /app.jar'
EXPOSE 6533 6534
ENTRYPOINT ["java","-Xms128m","-Xmx128m","-jar","/app.jar"]