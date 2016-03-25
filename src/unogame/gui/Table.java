package unogame.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Table extends JFrame{
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

    public Table() {
        super("UnoGame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(rootPanel);

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
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.X_AXIS));
        scrollPanel = new JScrollPane(cardPanel);
        cardPanel.add(Box.createRigidArea(new Dimension(5, 0)));
    }
}