#!/bin/bash
java -cp java.policy:UnoGame_Server_jar/UnoGame-Distributed.jar -Djava.security.policy=java.policy unogame.server.GameRegistration
