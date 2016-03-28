package unogame.gui;

import unogame.game.UnoCard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Table extends JFrame{
    private static final String CARD_IMG_PATH = "unogame/gui/images/Cards/";
    private static final String CARD_IMG_EXT = ".png";
    private static final String CARD_SELECTED = "Selected";

    private JButton play;
    private JPanel rootPanel, tablePanel, backgroundPanel;
    private JPanel mainPanel, infoAndButtonPanel;
    private JLabel turnCnt, turnCntLabel, background;
    private JLabel sumCards;
    private int cardsNum;
    private JLabel discardsDeckLabel;
    private JScrollPane scrollPanel;
    private JLabel player2Label;
    private JLabel player4Label;
    private JLabel player3Label;
    private JLabel deckLabel;
    private JPanel cardPanel;
    private JPanel selectedPanel;
    private UnoCard selectedCard;
    private JLabel selectedCaption;
    private JLabel selectedCardImage;

    public Table() {
        super("UnoGame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(rootPanel);

        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                cardsNum--;
                setSumCards(cardsNum);
                setDiscardedDeckFront();
                removeCard();
                selectedCard = null;
                selectedPanel = null;
                selectedCaption = null;
                selectedCardImage = null;
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

    public void addCard(final UnoCard card) {
        String cardImagePath = CARD_IMG_PATH+card.getCardID()+CARD_IMG_EXT;
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

    public void setSumCards(int n){
        cardsNum = n;
        sumCards.setText("+"+Integer.toString(n));
    }

    public void setTurnLabel(String s){
        turnCnt.setText(s);
    }

    public void removeCard() {
        if (selectedPanel != null){
            selectedPanel.setVisible(false);
            cardPanel.remove(selectedPanel);
            cardPanel.validate();
        }
    }

    public void setDiscardedDeckFront(){
        if (selectedPanel != null){
            discardsDeckLabel.setIcon(selectedCardImage.getIcon());
            discardsDeckLabel.validate();
        }
    }

}