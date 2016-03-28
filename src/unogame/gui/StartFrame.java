package unogame.gui;

import unogame.server.RemoteRegistration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class StartFrame extends JFrame{
    private ReentrantLock startLock;
    private Condition startCondition;

    public StartFrame(final int playerId) {
        super("Distributed UNO");

        Container testC = this.getContentPane();
        testC.setLayout(new FlowLayout());

        JLabel unoLabel = new JLabel("Press play button to start the game");
        JButton startPlayBtn = new JButton("Play");

        ActionListener startPlayBtnListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                startLock.lock();
                startCondition.signal();
                startLock.unlock();
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

    public void setStartTrigger(ReentrantLock lock, Condition condition){
        this.startLock = lock;
        this.startCondition = condition;
    }
}