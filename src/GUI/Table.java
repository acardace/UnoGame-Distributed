package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by ghosty on 16/03/16.
 */
public class Table extends JFrame{
    private JButton play;
    private JPanel rootPanel;
    private JPanel mainPanel;
    private JPanel testPanel;


    public Table(){
        super("UnoGame");
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(rootPanel);
        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showConfirmDialog(Table.this, "Clicked");
            }
        });



        setVisible(true);
    }

}
