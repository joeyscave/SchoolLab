package form;

import edu.hitsz.application.Main;
import edu.hitsz.settings.Settings;
import edu.hitsz.rank.RankDao;
import edu.hitsz.rank.RankDaoImp;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Rank {
    public JPanel rankPanel;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JLabel rankLabel;
    private JTable rankTable;
    private JButton deleteButton;
    private JScrollPane rankScrollPane;
    private JPanel nameOption;
    private JPanel deleteOption;
    private JLabel modeLable;

    private RankDao rank = new RankDaoImp();

    public Rank() {
        String[] mode={"easy","normal","hard"};
        SetMode();
        LoadTable();
        String name = JOptionPane.showInputDialog(rankPanel, "游戏结束，你的得分为" + Main.settings.score + "\n请输入名字记录得分:");
        if (name != null) {
            Date date = new Date();
            SimpleDateFormat ft = new SimpleDateFormat("MM-dd hh:mm");
            rank.add(name, Main.settings.score, ft.format(date));
            rank.store(mode[Settings.backGroundIndex/2]);
        }
        LoadTable();
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] options = {"是", "否", "取消"};
                int n = JOptionPane.showOptionDialog(rankPanel, "是否确定删除选中记录？", "选择一个选项", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if (n==0) {
                    int row = rankTable.getSelectedRow();
                    rank.remove(row);
                    LoadTable();
                    rank.store(mode[Settings.backGroundIndex/2]);
                }
            }
        });
    }

    private void LoadTable() {
        String[] columnName = {"排名", "玩家名", "得分", "记录时间"};
        DefaultTableModel model = new DefaultTableModel(rank.toTable(), columnName) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        rankTable.setModel(model);
        rankScrollPane.setViewportView(rankTable);

    }

    private void SetMode(){
        String[] options={"Easy","Normal","Hard"};
        modeLable.setText("难度："+options[Settings.backGroundIndex/2]);
    }

    class MyDialog extends JDialog {
        public MyDialog(JFrame frame) {
            super(frame, "输入");
            Container conn = getContentPane();
            conn.add(new JLabel("test"));
            setBounds(100, 100, 100, 100);
        }
    }

    public JPanel getRankPanel() {
        return rankPanel;
    }
}
