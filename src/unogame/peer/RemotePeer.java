package unogame.peer;

import unogame.game.Color;
import unogame.game.UnoCard;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

public interface RemotePeer extends Remote{
    void getGameToken(int cardsToPick, Color color) throws RemoteException;
    void getFTToken() throws RemoteException;
    int getID() throws RemoteException;
    void isAlive() throws RemoteException;
    void reconfigureRing(ArrayList<Integer> crashedPeers) throws RemoteException;
    boolean hasGToken() throws RemoteException;
    void addPlayers(HashMap<Integer, String> peers) throws RemoteException;
    void getGlobalState(int sender, int hand_cnt, int howManyPicked, UnoCard card, int direction, int turnOfPlayer) throws RemoteException;
    void setGlobalState(UnoCard card, int direction) throws RemoteException;
    void announceSkip() throws RemoteException;
    void announceLost() throws RemoteException;
    void announcePlayerAboutToWin(int id) throws RemoteException;
    void announceWon(int id) throws RemoteException;
    void playerWon() throws RemoteException;
    void redoStep() throws RemoteException;
    int getClock() throws RemoteException;
}
