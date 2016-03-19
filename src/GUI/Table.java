package GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.text.AttributedCharacterIterator;

/**
 * Created by ghosty on 16/03/16.
 */
public class Table extends JFrame{
    private JButton play;
    private JPanel rootPanel;
    private JPanel mainPanel;
    private JPanel tablePanel;
    private JPanel backgroundPanel;
    private JLabel background;
    private JPanel player1;
    private JPanel player2;
    private JPanel player3;
    private JPanel player4;
    private JPanel player5;
    private JPanel player6;
    private JPanel player7;
    private JPanel deck;
    private JPanel scartedDeck;
    private JLabel deckImage;
    private JLabel scartedImage;
    private JPanel myCartContainer;
    private JPanel card01;
    private JPanel card02;
    private JPanel card03;
    private JLabel imageCard01;
    private JPanel card04;
    private JPanel card05;
    private JPanel card06;
    private JPanel card07;
    private JPanel card08;
    private JPanel card09;
    private JPanel card10;
    private JPanel card11;
    private JPanel card12;
    private JPanel card13;
    private JPanel card14;
    private JPanel card15;
    private JPanel card16;
    private JPanel card17;
    private JPanel card18;
    private JPanel card19;
    private JPanel card20;
    private JPanel card21;
    private JPanel card22;
    private JPanel card23;
    private JPanel card24;
    private JPanel card25;
    private JPanel card26;
    private JPanel card27;
    private JPanel card28;
    private JPanel card29;
    private JPanel card30;
    private JPanel card31;
    private JPanel card32;
    private JPanel card33;
    private JPanel card34;
    private JPanel card35;
    private JPanel card36;
    private JPanel card37;
    private JPanel card38;
    private JPanel card39;
    private JPanel card40;
    private JPanel card41;
    private JPanel card42;
    private JPanel card43;
    private JPanel card44;
    private JPanel card45;
    private JPanel card46;
    private JPanel card47;
    private JPanel card48;
    private JPanel card49;
    private JPanel card50;
    private JLabel imageCard02;
    private JLabel imageCard03;
    private JLabel imageCard04;
    private JLabel imageCard05;
    private JLabel imageCard06;
    private JLabel imageCard07;
    private JLabel imageCard08;
    private JLabel imageCard09;
    private JLabel imageCard10;
    private JLabel imageCard11;
    private JLabel imageCard12;
    private JLabel imageCard13;
    private JLabel imageCard14;
    private JLabel imageCard15;
    private JLabel imageCard16;
    private JLabel imageCard17;
    private JLabel imageCard18;
    private JLabel imageCard19;
    private JLabel imageCard20;
    private JLabel imageCard21;
    private JLabel imageCard22;
    private JLabel imageCard23;
    private JLabel imageCard24;
    private JLabel imageCard25;
    private JLabel imageCard26;
    private JLabel imageCard27;
    private JLabel imageCard28;
    private JLabel imageCard29;
    private JLabel imageCard30;
    private JLabel imageCard31;
    private JLabel imageCard32;
    private JLabel imageCard33;
    private JLabel imageCard34;
    private JLabel imageCard35;
    private JLabel imageCard36;
    private JLabel imageCard37;
    private JLabel imageCard38;
    private JLabel imageCard39;
    private JLabel imageCard40;
    private JLabel imageCard41;
    private JLabel imageCard42;
    private JLabel imageCard43;
    private JLabel imageCard44;
    private JLabel imageCard45;
    private JLabel imageCard46;
    private JLabel imageCard47;
    private JLabel imageCard48;
    private JLabel imageCard49;
    private JLabel imageCard50;
    private JLabel turnCntLabel;
    private JLabel turnCnt;
    private JPanel infoAndButtonPanel;
    private JLabel sumCards;

    public Table(){
        super("UnoGame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(rootPanel);

        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showConfirmDialog(Table.this, "Clicked");
            }
        });



        pack();
//        background.add(tablePanel);
        tablePanel.setLayout(new BorderLayout());
        tablePanel.add(backgroundPanel);
        setVisible(true);
    }

}