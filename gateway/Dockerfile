FROM java:latest
VOLUME /tmp
ADD ./target/gateway.jar /app.jar
RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
  && echo 'Asia/Shanghai' >/etc/timezone \
RUN bash -c 'touch /app.jar'
EXPOSE 6505
ENTRYPOINT ["java","-Xms512m","-Xmx512m","-jar","/app.jar"]