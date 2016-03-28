#!/bin/bash
if ! pgrep "rmiregistry" > /dev/null
then
   echo "starting rmiregistry"
   rmiregistry &
fi
java -cp client.policy:UnoGame_Client_jar/UnoGame-Distributed.jar -Djava.security.policy=client.policy unogame.Main $1
