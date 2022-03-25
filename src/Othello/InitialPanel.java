package Othello;

import javax.swing.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.util.Scanner;

//初始界面
public class InitialPanel extends JPanel implements ActionListener ,MouseListener{
    public JLabel classicMode = new JLabel();//经典模式标签
    public JLabel readMode = new JLabel();//读取文件标签
    public JLabel computerMode = new JLabel();//人机模式标签
    public JLabel instruction = new JLabel();//说明标签
    private int width = 145;
    private int height = 55;

    public InitialPanel() {
        this.setSize(Constants.marginLeft + Constants.boardWidth + Constants.marginRight,
                Constants.marginTop + Constants.boardHeight + Constants.marginBottom);
        this.setLayout(null);
        this.setOpaque(false);

        //创建字体
        createFont();

        //创建经典模式按钮
        createClassicMode();

        //创建作弊模式按钮
        createReadMode();

        //创建人机模式按钮
        createComputerMode();

        //创建游戏说明按钮
        createInstruction();

        //事件重绘
        new Timer(delay, taskPerformer).start();
    }

    int delay = 100; //milliseconds 刷新间隔

    private void rep() {
        repaint();
    }

    //定时repaint 刷新整个画面
    ActionListener taskPerformer = new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            rep();
        }
    };

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //背景图
        g.drawImage(ImageValue.InitialBackGround, 0, 0, Constants.boardWidth + Constants.marginLeft + Constants.marginRight, Constants.boardHeight + Constants.marginTop + Constants.marginBottom, null);
    }

    //创建字体
    public Font createFont() {
        return new Font("华文行楷", Font.BOLD, 20);
    }

    //创建经典模式按钮
    public void createClassicMode() {
        classicMode.setSize(width, height);
        classicMode.setLocation(335-58, 300-4);
        ImageIcon a = new ImageIcon("src/images/开始游戏.png");
        Image b = a.getImage();
        Image c = b.getScaledInstance(width,height,java.awt.Image.SCALE_SMOOTH);
        ImageIcon d = new ImageIcon(c);
        classicMode.setIcon(d);
        classicMode.setEnabled(true);
        classicMode.addMouseListener(this);
        classicMode.setVisible(true);
//        classicMode.setOpaque(true);

        classicMode.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                (new anniu()).start();
                ClassicChoose newCP = new ClassicChoose();
                GameSystem.frame.setPanel(newCP);
            }
        });

        this.add(classicMode);
    }

    //创建读取文档按钮
    private void createReadMode() {
        readMode.setSize(width, height);
        readMode.setLocation(335-58, 467);
        ImageIcon a = new ImageIcon("src/images/读取存档.png");
        Image b = a.getImage();
        Image c = b.getScaledInstance(width,height,java.awt.Image.SCALE_SMOOTH);
        ImageIcon d = new ImageIcon(c);
        readMode.setIcon(d);
        readMode.setEnabled(true);
        readMode.addMouseListener(this);
        readMode.setVisible(true);
//        cheatingMode.setOpaque(true);

        readMode.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                super.mouseClicked(e);
                (new anniu()).start();
                String str = null;
                try {
                    str = read();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                if (str == null) {
                    JOptionPane.showMessageDialog(null, "文件不存在或文件读取失败", "错误", JOptionPane.PLAIN_MESSAGE);
                    return;
                }
                Scanner input = new Scanner(str);
                if(input.nextInt() == 0) {
                    GamePanel newGP = new GamePanel();
                    newGP.reading(str);
                    GameSystem.frame.setPanel(newGP);
                }else{
                    ComputerGame newgame = new ComputerGame();
                    newgame.reading(str);
                    newgame.AIPlayer2 =new MCTS(newgame.difficulty, 8, 10000000, 19260817);
                    GameSystem.frame.setPanel(newgame);
                }
            }
        });

        this.add(readMode);


    }

    //创建人机模式按钮
    private void createComputerMode() {
        computerMode.setSize(width, height);
        computerMode.setLocation(335-58, 381);
        ImageIcon a = new ImageIcon("src/images/人机对战.png");
        Image b = a.getImage();
        Image c = b.getScaledInstance(width,height,java.awt.Image.SCALE_SMOOTH);
        ImageIcon d = new ImageIcon(c);
        computerMode.setIcon(d);
        computerMode.setEnabled(true);
        computerMode.addMouseListener(this);
        computerMode.setVisible(true);
//        computerMode.setOpaque(true);

        computerMode.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                (new anniu()).start();
                ChoosePanel newCP = new ComputerChoose();
                GameSystem.frame.setPanel(newCP);
            }
        });

        this.add(computerMode);
    }

    //创建游戏说明按钮
    private void createInstruction() {
        instruction.setSize(width, height);
        instruction.setLocation(335-58, 550);
        ImageIcon a = new ImageIcon("src/images/帮助说明.png");
        Image b = a.getImage();
        Image c = b.getScaledInstance(width,height,java.awt.Image.SCALE_SMOOTH);
        ImageIcon d = new ImageIcon(c);
        instruction.setIcon(d);
        instruction.setEnabled(true);
        instruction.addMouseListener(this);
        instruction.setVisible(true);
//        instruction.setOpaque(true);
        this.add(instruction);
        instruction.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                super.mouseClicked(e);
                (new anniu()).start();
                JOptionPane.showMessageDialog(null, "othello：\n" +
                        "经典模式规则：把您颜色的战士置于棋盘的十字上，而当自己放下的棋子在横、竖、斜八个方向内有一个自己的棋子，\n" +
                        "被夹在中间的全部翻转会成为自己的棋子，并且，只有在可以翻转棋子的地方才可以下子。\n" +
                        "上帝模式规则：您可以跳脱出经典模式之外在棋盘中的任意空位下子~\n" +
                        "人机对战：基于输入的数字决定算法深度进而改变人机难度。", "帮助说明", JOptionPane.PLAIN_MESSAGE);

            }
        });


    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // 鼠标移入
        if (e.getSource().equals(classicMode)) {//e指一个事件。e.getSource()获取事件
            //如果鼠标移入到（start）组件（图片按钮）
            classicMode.setEnabled(false);
        } else if (e.getSource().equals(readMode)) {
            readMode.setEnabled(false);
        } else if (e.getSource().equals(computerMode)) {
            computerMode.setEnabled(false);
        } else if (e.getSource().equals(instruction)) {
            instruction.setEnabled(false);
        }
    }


    @Override
    public void mouseExited(MouseEvent e) {
        //鼠标移出
        if (e.getSource().equals(classicMode)) {
            classicMode.setEnabled(true);
        } else if (e.getSource().equals(readMode)) {
            readMode.setEnabled(true);
        } else if (e.getSource().equals(computerMode)) {
            computerMode.setEnabled(true);
        } else if (e.getSource().equals(instruction)) {
            instruction.setEnabled(true);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public String read() throws IOException {
        File file = new File("date\\");
        String filename = JOptionPane.showInputDialog(this, "请输入您要读取的游戏存档名");//"input the name here"
        String filePath = "date\\" + filename +".txt";
        File f = new File(filePath);
        if (f.exists()) {
            FileReader fr = null;
            try {
                fr = new FileReader(filePath);
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }

            BufferedReader br = new BufferedReader(fr);
            int peek;
            StringBuilder sin = new StringBuilder();
            while ((peek = fr.read()) != -1) sin.append((char) peek);
            br.close();
            String prs = sin.toString();
            try {
                br.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

            try {
                fr.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            return prs;
        } else {
            return null;
        }
    }
}
