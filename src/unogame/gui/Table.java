package unogame.gui;

import unogame.game.Number;
import unogame.game.Color;
import unogame.game.UnoCard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class Table extends JFrame{
    private static final String CARD_IMG_PATH = "unogame/gui/images/Cards/";
    private static final String CARD_IMG_EXT = ".png";

    private JButton play;
    private JPanel rootPanel, tablePanel, backgroundPanel;
    private JPanel player2, player4, player3;
    private JPanel mainPanel, discardedDeck, infoAndButtonPanel, deck;
    private JLabel turnCnt, turnCntLabel, background;
    private JLabel sumCards, discardedImage, deckImage;
    private JLabel player2Label;
    private JLabel player3Label;
    private JLabel player4Label;
    private JScrollPane scrollPanel;
    private ArrayList<JLabel> players;
    private JPanel cardPanel;
    private HashMap<String, UnoCardInHand> cardsInHand;

    public Table() {
        super("UnoGame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(rootPanel);

        cardsInHand = new HashMap<>();
        players = new ArrayList<JLabel>();
        players.add(player2Label);
        players.add(player3Label);
        players.add(player4Label);
//        for (JLabel player: players){
//            player.setIcon(null);
//        }

        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

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

    public void addCard(UnoCard card) {
        UnoCardInHand cardInHand;
        String cardImagePath = CARD_IMG_PATH+card.getCardID()+CARD_IMG_EXT;
        JLabel cardImage = new JLabel(new ImageIcon(cardImagePath));
        if( cardsInHand.containsKey(card.getCardID()) ){
            cardInHand = cardsInHand.get(card.getCardID());
            cardInHand.num += 1;
        }
        else {
            cardInHand = new UnoCardInHand(card, cardImage,1);
        }
        cardsInHand.put( card.getCardID(), cardInHand);
        cardPanel.add(cardImage);
        cardPanel.validate();
    }

    public void removeCard(UnoCard card) {
        if (cardsInHand.containsKey(card.getCardID())){
            UnoCardInHand unoCardInHand = cardsInHand.get(card.getCardID());
            if (unoCardInHand.num == 1) {
                cardPanel.remove(unoCardInHand.cardLabel);
                cardsInHand.remove(card.getCardID());
            }
            else {
                cardPanel.remove(unoCardInHand.cardLabel);
                unoCardInHand.num -= 1;
                cardsInHand.put(card.getCardID(), unoCardInHand);
            }
            cardPanel.validate();
        }
    }

    //inner class for utility
    public class UnoCardInHand {
        public UnoCard unoCard;
        public JLabel cardLabel;
        public int num;

        public UnoCardInHand(UnoCard card, JLabel label, int n){
            unoCard = card;
            cardLabel = label;
            n = num;
        }
    }
}