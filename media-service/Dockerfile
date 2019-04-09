FROM java:latest
VOLUME /tmp
ADD ./target/media-service.jar /app.jar
RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
  && echo 'Asia/Shanghai' >/etc/timezone \
RUN bash -c 'touch /app.jar'
EXPOSE 6506
ENTRYPOINT ["java","-Xms3072m","-Xmx5120m","-jar","/app.jar"]