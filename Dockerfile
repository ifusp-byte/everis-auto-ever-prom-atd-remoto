FROM ifusp.artifactory.net/developers-java/oracle-jre.rhel8/11e64:latest

WORKDIR /app 

COPY target/empresa-pix.jar ./app.jar

ADD src/scripts/entry-point.sh entry-point.sh
RUN exec chmod 755 entry-point.sh
# OpenShift picks a random UID at run-time. Image must be able to run as any user.
RUN chmod g+rwx -R . 
RUN chown -R 1001:0 /opt/appdyn && \
    find /opt/appdyn -exec chfrp 0 {} \; && \
    find /opt/appdyn -exec chmod g+rw {} \; && \
    find /opt/appdyn -type d -exec chmod g+x {} +
ENTRYPOINT ["./entry-point.sh"]