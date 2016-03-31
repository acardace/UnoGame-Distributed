package unogame.gui;

import unogame.game.UnoCard;
import unogame.game.UnoDeck;
import unogame.game.UnoPlayer;
import unogame.game.UnoRules;
import unogame.peer.GamePeer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.rmi.RemoteException;

public class GUITable extends JFrame{
    private static final String CARD_IMG_PATH = "/images/Cards/";
    private static final String CARD_IMG_EXT = ".png";
    private static final String CARD_SELECTED = "Selected";

    private JButton play;
    private JPanel rootPanel, tablePanel, backgroundPanel;
    private JPanel mainPanel, infoAndButtonPanel;
    private JLabel turnCnt, turnCntLabel, background;
    private JLabel sumCards;
    private JLabel discardsDeckLabel;
    private JScrollPane scrollPanel;
    private JLabel player2Label;
    private JLabel player4Label;
    private JLabel player3Label;
    private JLabel deckLabel;
    private JButton draw;
    private JPanel cardPanel;
    private JPanel selectedPanel;
    private UnoCard selectedCard;
    private JLabel selectedCaption;
    private JLabel selectedCardImage;
    private GamePeer gamePeer;
    private UnoPlayer unoPlayer;
    private UnoDeck unoDeck;

    public GUITable(final GamePeer gamePeer) {
        super("UnoGame");
        this.gamePeer = gamePeer;
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setContentPane(rootPanel);

        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (selectedCard == null) {
                    JOptionPane.showMessageDialog(rootPanel, "Select a card!", "Fool", JOptionPane.INFORMATION_MESSAGE);
                } else if (!gamePeer.hasGToken()) {
                    JOptionPane.showMessageDialog(rootPanel, "It's not your turn!", "Fool", JOptionPane.INFORMATION_MESSAGE);
                } else if (!UnoRules.isPlayable(selectedCard)){
                    JOptionPane.showMessageDialog(rootPanel, "Card not playable!", "Fool", JOptionPane.INFORMATION_MESSAGE);
                }else{
                    setDiscardedDeckFront(null);
                    removeCard();
                    setTurnLabel("Nope");
                    playCard(selectedCard);
                    selectedCard = null;
                    selectedPanel = null;
                    selectedCaption = null;
                    selectedCardImage = null;
                }
            }
        });

        draw.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(!gamePeer.hasGToken()){
                    JOptionPane.showMessageDialog(rootPanel, "It's not your turn!", "Fool", JOptionPane.INFORMATION_MESSAGE);
                }else{
                    addCard(unoPlayer.getCardfromDeck(unoDeck));
                    setTurnLabel("Nope");
                    try{
                        gamePeer.sendGameToken();
                    }catch (RemoteException e){
                        System.err.println("playCard: sendGameToken() failed");
                    }
                }
            }
        });


        pack();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.add(backgroundPanel);
        setVisible(true);
    }

    private void createUIComponents() {
        //cardPanel is the container of the player cards
        cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.LINE_AXIS));
        scrollPanel = new JScrollPane(cardPanel);
        cardPanel.add(Box.createRigidArea(new Dimension(5, 0)));
    }

    private void playCard(UnoCard card){
        unoPlayer.playCard(card, unoDeck);
        System.out.println("Discard Deck");
        for (UnoCard cardinDeck: unoDeck.stackDiscardDeck)
            System.out.println(cardinDeck.getCardID());
        try {
            gamePeer.sendGameToken();
        }catch (RemoteException e){
            System.err.println("playCard: sendGameToken() failed");
        }
    }

    private void addCard(final UnoCard card) {
        URL cardImagePath = getClass().getResource(CARD_IMG_PATH+card.getCardID()+CARD_IMG_EXT);
        final JLabel cardImage = new JLabel(new ImageIcon(cardImagePath));
        final JPanel cardContainer = new JPanel();
        final JLabel cardCaption = new JLabel();
        cardContainer.setLayout(new BoxLayout(cardContainer, BoxLayout.Y_AXIS));
        cardContainer.add(cardCaption);
        cardContainer.add(cardImage);

        //setting up listener for clicks on cards
        cardImage.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if( selectedPanel != null && selectedPanel != cardContainer){
                    selectedCaption.setText("");
                    selectedCaption.validate();
                }
                if (cardCaption.getText().equals(CARD_SELECTED)) {
                    selectedCard = null;
                    selectedPanel = null;
                    selectedCaption = null;
                    selectedCardImage = null;
                    cardCaption.setText("");
                    cardCaption.validate();
                } else {
                    selectedCard = card;
                    selectedPanel = cardContainer;
                    selectedCaption = cardCaption;
                    selectedCardImage = cardImage;
                    cardCaption.setText("Selected");
                    cardCaption.validate();
                }
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {

            }
        });
        cardPanel.add(cardContainer);
        cardPanel.validate();
    }

    public void setPlusEventLabel(int n){
        sumCards.setText("+"+Integer.toString(n));
    }

    public void setTurnLabel(String s){
        turnCnt.setText(s);
    }

    private void removeCard() {
        if (selectedPanel != null){
            selectedPanel.setVisible(false);
            cardPanel.remove(selectedPanel);
            cardPanel.validate();
        }
    }

    public void setDiscardedDeckFront(String cardID){
        if (selectedPanel != null && cardID == null){
            discardsDeckLabel.setIcon(selectedCardImage.getIcon());
            discardsDeckLabel.validate();
        }else if( cardID != null){
            URL cardImagePath = getClass().getResource(CARD_IMG_PATH+cardID+CARD_IMG_EXT);
            discardsDeckLabel.setIcon(new ImageIcon(cardImagePath));
            discardsDeckLabel.validate();
        }
    }

    public void initGame(){
        unoPlayer = gamePeer.getUnoPlayer();
        unoDeck = gamePeer.getUnoDeck();
        gamePeer.setCallbackObject(this);
        gamePeer.initialHand();
        unoDeck.setHowManyPicked(0);
        sumCards.setVisible(false);
        for (UnoCard card: unoPlayer.getHand())
            addCard(card);
        //TODO change the label in something significant
        if (gamePeer.hasGToken())
            setTurnLabel("Your Turn");
        else
            setTurnLabel("Nope");
    }

}