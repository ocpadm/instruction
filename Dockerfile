FROM nocom/eap7.0:latest
COPY frontend-web/target/*.war /opt/jboss/wildfly/standalone/deployments
COPY backend-web/target/*.war /opt/jboss/wildfly/standalone/deployments
RUN mkdir -p /opt/jboss/wildfly/standalone/configuration/frontend-web && \
    mkdir -p /opt/jboss/wildfly/standalone/configuration/backend-web 
COPY backend-config/SIT/*.properties /opt/jboss/wildfly/standalone/configuration/backend-web/
COPY frontend-config/SIT/*.properties /opt/jboss/wildfly/standalone/configuration/frontend-web/
RUN chown -R jboss:jboss /opt/jboss/wildfly/standalone/configuration
RUN curl --silent --insecure -o /opt/jboss/wildfly/bin/initial.cli https://mct/rest/profile/cli/333
RUN /opt/jboss/wildfly/bin/execute.sh
EXPOSE 8080
CMD /opt/jboss/wildfly/bin/standalone.sh