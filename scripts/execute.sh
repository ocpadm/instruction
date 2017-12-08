#!/bin/bash
JBOSS_CLI=$JBOSS_HOME/bin/jboss-cli.sh
function wait_for_server() {
  until `$JBOSS_CLI -c "ls /deployment" &> /dev/null`; do
    sleep 10
    echo "sleeping 10 seconds"
  done
}
echo "=> Starting JBoss server"
$JBOSS_HOME/bin/standalone.sh -c standalone.xml > /dev/null &

echo "=> Waiting for the server to boot"
wait_for_server

echo "=> Executing the commands"
$JBOSS_CLI -c --file=`dirname "$0"`/initial.cli

echo "=> Shutting down WildFly"
$JBOSS_CLI -c ":shutdown"

echo "=> Starting WildFly server again"
$JBOSS_HOME/bin/standalone.sh -c standalone.xml > /dev/null &
