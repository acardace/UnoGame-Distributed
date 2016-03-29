#!/bin/bash
java -cp java.policy:UnoGame_Client_jar/UnoGame-Distributed.jar -Djava.security.policy=java.policy unogame.Main $1
