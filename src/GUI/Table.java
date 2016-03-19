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
    private JPanel myCardsPanel;
    private JPanel tablePanel;
    private JLabel background;

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
        background.add(tablePanel);
        setVisible(true);
    }

}