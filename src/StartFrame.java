import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by michele on 27/02/16.
 */
public class StartFrame extends JFrame {

    public StartFrame(Main startObj) {
        super("Distributed UNO");

        Container testC = this.getContentPane();
        testC.setLayout(new FlowLayout());

        JLabel unoLabel = new JLabel("Press play button to start the game");
        JButton startPlayBtn = new JButton("Play");

        startPlayBtn.addActionListener(startObj);

        this.add(unoLabel);
        this.add(startPlayBtn);

        this.setSize(400,70);
        this.setLocation(760, 340);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}