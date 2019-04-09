FROM java:latest
VOLUME /tmp
ADD ./target/zipkin.jar /app.jar
RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
  && echo 'Asia/Shanghai' >/etc/timezone \
RUN bash -c 'touch /app.jar'
EXPOSE 6504
ENTRYPOINT ["java","-Xms256m","-Xmx1024m","-XX:-UseGCOverheadLimit","-jar","/app.jar"]