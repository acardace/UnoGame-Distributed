#!/bin/bash
if ! pgrep "rmiregistry" > /dev/null
then
   echo "starting rmiregistry"
   rmiregistry &
fi
java -cp server.policy:UnoGame_Server_jar/UnoGame-Distributed.jar -Djava.security.policy=server.policy unogame.server.GameRegistration
