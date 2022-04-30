package edu.hitsz.application;

import javax.swing.*;
import java.awt.*;

import edu.hitsz.settings.Settings;
import edu.hitsz.settings.SettingsEasy;
import edu.hitsz.settings.SettingsHard;
import edu.hitsz.settings.SettingsNormal;
import form.*;
import form.Menu;

/**
 * 程序入口
 *
 * @author hitsz
 */
public class Main {

    public static final int WINDOW_WIDTH = 512;
    public static final int WINDOW_HEIGHT = 768;
    public static final Object Main_LOCK = new Object();
    public static final Object Bullet_LOCK = new Object();
    public static Settings settings;

    public static void main(String[] args) {

        System.out.println("Hello Aircraft War");
        // 获得屏幕的分辨率，初始化 Frame
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        JFrame frame = new JFrame("Aircraft War");
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setResizable(false);
        //设置窗口的大小和位置,居中放置
        frame.setBounds(((int) screenSize.getWidth() - WINDOW_WIDTH) / 2, 0,
                WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 菜单页面
        Menu newFrame = new Menu();
        JPanel newMainPanel = newFrame.getMenuPanel();
        frame.setContentPane(newMainPanel);
        frame.setVisible(true);

        synchronized (Main_LOCK) {
            while (newMainPanel.isVisible()) {
                try {
                    Main_LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        frame.remove(newMainPanel);

        // 初始化游戏
        switch (Settings.backGroundIndex / 2) {
            case 0:
                settings = new SettingsEasy();
                break;
            case 1:
                settings = new SettingsNormal();
                break;
            case 2:
                settings = new SettingsHard();
                break;
            default:
        }
        settings.setGame();

        // 游戏页面
        Game game = new Game();
        frame.setContentPane(game);
        frame.setVisible(true);
        game.action();

        synchronized (Main_LOCK) {
            while (game.isVisible()) {
                try {
                    Main_LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        frame.remove(game);

        // 排行榜页面
        Rank rankFrame = new Rank();
        JPanel rankPanel = rankFrame.getRankPanel();
        frame.setContentPane(rankPanel);
        frame.setVisible(true);
    }
}
