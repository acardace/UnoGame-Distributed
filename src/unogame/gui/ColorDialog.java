package unogame.gui;

import unogame.game.Color;

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
    private Color selectedColor;

    public ColorDialog() {
        setContentPane(contentPane);

        blueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                selectedColor = Color.BLUE;
                dispose();
            }
        });

        greenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                selectedColor = Color.GREEN;
                dispose();
            }
        });

        redButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                selectedColor = Color.RED;
                dispose();
            }
        });

        yellowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                selectedColor = Color.YELLOW;
                dispose();
            }
        });
    }

    public Color selectColor(){
        pack();
        setLocationRelativeTo(null);
        setModal(true);
        setVisible(true);
        return selectedColor;
    }

}
