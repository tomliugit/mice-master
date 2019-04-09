FROM java:latest
VOLUME /tmp
ADD ./target/elasticsearch-service.jar /app.jar
RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
  && echo 'Asia/Shanghai' >/etc/timezone \
RUN bash -c 'touch /app.jar'
EXPOSE 6516
ENTRYPOINT ["java","-Xms1024m","-Xmx2048m","-jar","/app.jar"]