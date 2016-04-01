package unogame.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class
ColorDialog extends JDialog {
    private JPanel contentPane;
    private JButton blueButton;
    private JButton greenButton;
    private JButton redButton;
    private JButton yellowButton;

    public ColorDialog() {
        setContentPane(contentPane);

        blueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });

        greenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });

        redButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });

        yellowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });

        pack();
        setLocationRelativeTo(null);
        setModal(true);
        setVisible(true);
    }

}
