/**
 * Created by michele on 16/02/16.
 */
public class PlayerReady {
    RemotePeer playerRef;
    boolean playerReady;

    public PlayerReady(RemotePeer ref, boolean ready) {
        this.playerReady = ready;
        this.playerRef = ref;
    }
}
