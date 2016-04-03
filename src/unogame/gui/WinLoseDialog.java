package unogame.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WinLoseDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JLabel resultLabel;

    public WinLoseDialog(final GUITable guiTable) {
        setContentPane(contentPane);
        buttonOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                guiTable.dispose();
                System.exit(0);
            }
        });
    }

    public void showDialog(String s) {
        resultLabel.setText(s);
        pack();
        setLocationRelativeTo(null);
        setModal(false);
        setVisible(true);
    }
}
