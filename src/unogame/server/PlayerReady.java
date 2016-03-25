package unogame.server;

import unogame.peer.RemotePeer;

/**
 * Created by michele on 16/02/16.
 */
public class PlayerReady {
    String addr;
    RemotePeer playerRef;
    boolean playerReady;

    public PlayerReady(String addr, RemotePeer ref, boolean ready) {
        this.addr = addr;
        this.playerReady = ready;
        this.playerRef = ref;
    }
}
