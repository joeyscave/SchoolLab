package form;

import edu.hitsz.application.Main;
import edu.hitsz.settings.Settings;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu {
    public JPanel menuPanel;
    private JButton simple;
    private JButton middle;
    private JButton hard;
    private JCheckBox audio;

    public Menu() {
        audio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Settings.audio = !Settings.audio;
            }
        });
        simple.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Settings.backGroundIndex = 0;
                jump();
            }
        });
        middle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Settings.backGroundIndex = 2;
                jump();
            }
        });
        hard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Settings.backGroundIndex = 4;
                jump();
            }
        });
    }

    public void jump() {
        menuPanel.setVisible(false);
        synchronized (Main.Main_LOCK) {
            Main.Main_LOCK.notify();
        }
    }

    public JPanel getMenuPanel() {
        return menuPanel;
    }
}
