package unogame.server;

/**
 * Created by michele on 16/02/16.
 */
public class PlayersRegistrationData {
    int playerID;
    int[] playersID;

    public PlayersRegistrationData(int id, int[] allID) {
        this.playerID = id;
        this.playersID = allID;
    }
}
