import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartFrame extends JFrame{

    public StartFrame() {
        super("Distributed UNO");

        Container testC = this.getContentPane();
        testC.setLayout(new FlowLayout());

        JLabel unoLabel = new JLabel("Press play button to start the game");
        JButton startPlayBtn = new JButton("Play");

        ActionListener startPlayBtnListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("Button Clicked");
            }
        };
        startPlayBtn.addActionListener(startPlayBtnListener);

        this.add(unoLabel);
        this.add(startPlayBtn);

        this.setSize(400,70);
        this.setLocation(760, 340);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}