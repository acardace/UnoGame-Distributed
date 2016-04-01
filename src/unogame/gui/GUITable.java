package unogame.gui;

import unogame.game.*;
import unogame.game.Color;
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
                } else if (!UnoRules.isPlayable(selectedCard)){
                    JOptionPane.showMessageDialog(rootPanel, "Card not playable!", "Fool", JOptionPane.INFORMATION_MESSAGE);
                }else{
                    if (selectedCard.isSpecial() && selectedCard.getColor() == Color.BLACK) {
                        //TESTING
                        unoPlayer.setSelectedColor(Color.GREEN);
                        UnoRules.setCurrentColor(Color.GREEN);
                        if(selectedCard.isPlus()){
                            if(selectedCard.getType() == SpecialType.PLUS2)
                                setEventLabel(2, Color.GREEN);
                            else
                                setEventLabel(4, Color.GREEN);
                        }else
                            setEventLabel(0, Color.GREEN);
                    }else {
                        clearEventLabel();
                    }
                    play.setEnabled(false);
                    draw.setEnabled(false);
                    unoPlayer.setPlayedCard(true);
                    setDiscardedDeckFront(null);
                    setTurnLabel("Nope");
                    removeCard();
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
                draw.setEnabled(false);
                addCard(unoPlayer.getCardfromDeck(unoDeck));
                if (!UnoRules.hasSomethingPlayable(unoPlayer.getHand())) {
                    unoPlayer.setPlayedCard(false);
                    setTurnLabel("Nope");
                    play.setEnabled(false);
                    clearEventLabel();
                    try {
                        gamePeer.sendGameToken();
                    } catch (RemoteException e) {
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
        try {
            gamePeer.sendGameToken();
        }catch (RemoteException e){
            System.err.println("playCard: sendGameToken() failed");
        }
    }

    public void allowDrawing(){
        draw.setEnabled(true);
    }

    public void allowPlaying(){
        play.setEnabled(true);
    }

    public void addCard(final UnoCard card) {
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

    public void setEventLabel(int n, Color color){
        String text = "";
        if( color != null ) {
            switch (color) {
                case GREEN:
                    text = "Green";
                    break;
                case RED:
                    text = "Red";
                    break;
                case BLUE:
                    text = "Blue";
                    break;
                case YELLOW:
                    text = "Yellow";
                    break;
            }
        }
        if( n > 0 )
            text += "+"+Integer.toString(n);
        sumCards.setText(text);
        sumCards.validate();
    }

    public void clearEventLabel(){
        sumCards.setText("");
        sumCards.validate();
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
        clearEventLabel();
        for (UnoCard card: unoPlayer.getHand())
            addCard(card);
        //TODO change the label in something significant
        if (gamePeer.hasGToken()) {
            setTurnLabel("Your Turn");
        }else {
            setTurnLabel("Nope");
            draw.setEnabled(false);
            play.setEnabled(false);
        }
    }

}