package Othello;

import javax.swing.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class GamePanel extends JPanel implements ActionListener, MouseListener {

    public String name = new String();

    private JLabel blackCounter = new JLabel();//创建黑方计数器标签
    private JLabel whiteCounter = new JLabel();//创建白方计数器标签
    private JLabel retract = new JLabel();//创建悔棋标签
    private JLabel restart = new JLabel();//创建重新开始标签
    public JLabel blackName = new JLabel();//创建黑方显示器
    public JLabel whiteName = new JLabel();//创建白方显示器
    public JCheckBox god = new JCheckBox();//创建上帝模式标签
    public JLabel save = new JLabel();//创建保存按钮
    public JLabel initial = new JLabel();//返回初始界面
    public JLabel godMode = new JLabel();//上帝模式字样
    public String blackname = null;//黑方姓名
    public String whitename = null;//白方姓名
    public JLabel show = new JLabel();//回放
    public boolean showing = false;
    public int manType = 0;


    private final int ROWS = 8;//行数
    private final int COLUMNS = 8;//列数

    private String gameFlag = "start";//游戏状态

    private int mouseLastX, mouseLastY;//按下鼠标的坐标

    public boolean isCheatModeWhite = false, isCheatModeBlack = false;//某一方作弊与否

    int delay = 10; //milliseconds 刷新间隔

    private void rep() {
        repaint();
    }

    //定时repaint 刷新整个画面
    ActionListener taskPerformer = new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            if(!showing) {
                blackName.setText(blackname);
                whiteName.setText(whitename);


                String counter = String.valueOf(BlackCount());
                blackCounter.setText(counter);
                String counter1 = String.valueOf(WhiteCount());
                whiteCounter.setText(counter1);

                for (int i = 1; i <= 8; i++) {
                    for (int j = 1; j <= 8; j++) {
                        points[i - 1][j - 1].setType(possiblePlace(ChessBoards.get(ChessBoards.size() - 1), nextType)[j][i]);
                    }
                }


                rep();
            }
        }
    };

    //存棋盘集合
    public ArrayList<int[][]> ChessBoards = new ArrayList<int[][]>();

    //创建初始棋盘并将其加入棋盘数组里, 并加入首个nextType
    public void createChessboards() {
        int[][] board = new int[8 + 2][8 + 2];
        board[5][4] = -1;
        board[4][5] = -1;
        board[5][5] = 1;
        board[4][4] = 1;
//        board[8][7] = 1;
//        board[8][8] = -1;
//        board[7][8] = 1;
        ChessBoards.add(board);
        unitize();
        nextTypes.add(-1);
    }

    //创建指示器二维数组
    Pointer[][] pointers = new Pointer[ROWS][COLUMNS];

    //创建棋子二维数组
    Qizi[][] qiziss = new Qizi[ROWS][COLUMNS];

    //构造器
    public GamePanel() {
        this.setSize(Constants.marginLeft + Constants.boardWidth + Constants.marginRight,
                Constants.marginTop + Constants.boardHeight + Constants.marginBottom);
        this.setLayout(null);//布局
        this.setOpaque(false);

        //创建指示器数组内容
        createPointers();

        //创建棋子数组内容
        createQizis();

        //创建鼠标监听
        createMouseListener();

        //创建棋盘数组
        createChessboards();

        //创建黑方计数器
        createBlackCounter();

        //创建白方计数器
        createWhiteCounter();

        //创建悔棋键
        createRetract();

        //创建重新开始键
        createRestart();

        //创建黑方姓名显示器
        createBlackName();

        //创建白方姓名显示器
        createWhiteName();

        //创建点点数组
        createPoints();

        //创建上帝模式字样
        createGodMode();

        //创建上帝模式选择器
        createGod();

        //创建回到开始按钮
        createInitial();

        //创建存档按钮
        createSave();

        //创建回放按钮
        createShow();

        //事件重绘
            new Timer(delay, taskPerformer).start();
    }

    //创建回访按钮
    private void createShow(){
        ImageIcon a = new ImageIcon("src/images/回放.png");
        Image b = a.getImage();
        Image c = b.getScaledInstance(90, 56, java.awt.Image.SCALE_SMOOTH);
        ImageIcon d = new ImageIcon(c);
        show.setIcon(d);
        show.setEnabled(true);
        show.addMouseListener(this);
        show.setSize(90, 56);
        show.setLocation(24, 96);
        show.setOpaque(false);
        show.setVisible(true);
        this.add(show);
        show.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                showing = true;
                (new Replace()).start();
                showing = false;
            }
        });
    }

    //创建棋子数组内容
    private void createQizis() {
        int x = 0;
        int y = 0;
        int start = 40;
        Qizi qizi;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                x = j * 60 + start;
                y = i * 60 + start;
                qizi = new Qizi(i, j, x, y, 0);
                qiziss[i][j] = qizi;
            }
        }
    }

    //创建点点
    Point[][] points = new Point[ROWS][COLUMNS];

    private void createPoints() {
        int x = 0;
        int y = 0;
        int start = 40;
        Point point;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                x = j * 60 + start + 100;
                y = i * 60 + start + 100;
                point = new Point(i, j, x, y, 0);
                points[i][j] = point;
            }
        }
    }

    //创建数组内容
    private void createPointers() {
        int x = 0;
        int y = 0;
        int start = 40;
        Pointer pointer;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                x = j * 60 + start;
                y = i * 60 + start;
                pointer = new Pointer(i, j, x, y);
                pointers[i][j] = pointer;
            }
        }
    }

    int nextType = -1;
    ArrayList<Integer> nextTypes = new ArrayList<>();
    Reminder reminder = new Reminder(-1);

    //创建鼠标监听事件
    private void createMouseListener() {
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (!"start".equals(gameFlag)) return;

                //获取鼠标坐标
                int x = e.getX() - Constants.marginLeft;
                int y = e.getY() - Constants.marginTop;
                //循环指示器二维数组
                Pointer pointer;
                for (int i = 0; i < ROWS; i++) {
                    for (int j = 0; j < COLUMNS; j++) {
                        //得到每一个指示器的对象
                        pointer = pointers[i][j];
                        if (pointer.isPoint(x, y) && pointer.getType() == 0) {
                            if ((nextType == 1 && isCheatModeWhite) || (nextType == -1 && isCheatModeBlack) || possiblePlace(ChessBoards.get(ChessBoards.size() - 1), nextType)[j + 1][i + 1] != 0) {
                                pointer.setShow(true);
                                continue;
                            }
                        }
                        pointer.setShow(false);
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

                //设置计数器文本并使它可以改变
                String counter = String.valueOf(BlackCount());
                blackCounter.setText(counter);
                String counter1 = String.valueOf(WhiteCount());
                whiteCounter.setText(counter1);

                mouseLastX = e.getX() - Constants.marginLeft;
                mouseLastY = e.getY() - Constants.marginTop;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                int offset = 4;
                if (Math.abs(e.getX() - mouseLastX) <= offset && Math.abs(e.getY() - mouseLastY) <= offset) {

                    mouseClicked(e);
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (!"start".equals(gameFlag)) return;

                //获取鼠标坐标
                int x = e.getX() - Constants.marginLeft;
                int y = e.getY() - Constants.marginTop;
                //循环指示器二维数组
                Pointer pointer;
                Qizi qizi;
                for (int i = 0; i < ROWS; i++) {
                    for (int j = 0; j < COLUMNS; j++) {
                        //得到每一个指示器的对象
                        pointer = pointers[i][j];
                        //得到每一个棋子对象
                        qizi = qiziss[i][j];
                        if (pointer.isPoint(x, y) && pointer.getType() == 0) {
                            if ((nextType == 1 && isCheatModeWhite) || (nextType == -1 && isCheatModeBlack) || possiblePlace(ChessBoards.get(ChessBoards.size() - 1), nextType)[j + 1][i + 1] != 0) {
                                (new luozi()).start();
                                pointer.setType(nextType);

                                nextType = -nextType;

                                int[][] board = new int[8 + 2][8 + 2];
                                for (int ii = 1; ii <= 8; ii++) {
                                    for (int jj = 1; jj <= 8; jj++) {
                                        board[ii][jj] = ChessBoards.get(ChessBoards.size() - 1)[ii][jj];
                                    }
                                }

                                ChessBoards.add(change(board, qizi.getXx(), qizi.getYy(), -nextType));

                                if (!flag(possiblePlace(ChessBoards.get(ChessBoards.size() - 1), nextType))) {
                                    nextType = -nextType;
                                }
                                nextTypes.add(nextType);
                                reminder.setType(nextTypes.get(nextTypes.size() - 1));

                                unitize();

                                if (!flag(possiblePlace(ChessBoards.get(ChessBoards.size() - 1), 1)) && !flag(possiblePlace(ChessBoards.get(ChessBoards.size() - 1), -1))) {
                                    repaint();
                                    gameFlag = "stop";
                                    System.out.println(BlackCount() + WhiteCount());
                                    if (BlackCount() > WhiteCount()) {
                                        winner("黑方胜利");
                                    } else if (BlackCount() < WhiteCount()) {
                                        winner("白方胜利");
                                    } else {
                                        winner("平局");
                                    }
                                    gameFlag = "start";
                                    System.out.println("游戏结束");
                                }
                                return;
                            }
                        }
                    }
                }
            }
        };
        addMouseMotionListener(mouseAdapter);
        addMouseListener(mouseAdapter);
    }

    //创建字体
    public Font createFont() {
        return new Font("华文行楷", Font.BOLD, 25);
    }

    //黑方棋子计数
    public int BlackCount() {
        int blackcount = 0;
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                if (ChessBoards.get(ChessBoards.size() - 1)[i][j] == -1) {
                    blackcount++;
                }
            }
        }
        return blackcount;
    }

    //白方棋子计数
    public int WhiteCount() {
        int whitecount = 0;
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                if (ChessBoards.get(ChessBoards.size() - 1)[i][j] == 1) {
                    whitecount++;
                }
            }
        }
        return whitecount;
    }

    //创建黑方计数器
    public void createBlackCounter() {
        //取得字体
        Font tFont = createFont();

        blackCounter.setFont(tFont);
        blackCounter.setSize(40, 40);
        blackCounter.setLocation(147, 642);
        blackCounter.setVisible(true);
//        blackCounter.setOpaque(true);
        this.add(blackCounter);
    }

    //创建白方计数器
    public void createWhiteCounter() {
        //取得字体
        Font tFont = createFont();

        whiteCounter.setFont(tFont);
        whiteCounter.setSize(40, 40);
        whiteCounter.setLocation(522, 19);
        whiteCounter.setVisible(true);
//        whiteCounter.setOpaque(true);
        this.add(whiteCounter);
    }

    //创建黑方姓名显示器
    public void createBlackName() {
        //取得字体
        Font tFont = new Font("华文中宋", Font.BOLD, 25);

        blackName.setFont(tFont);

        blackName.setSize(150, 30);
        blackName.setLocation(164, 585);
        blackName.setVisible(true);
//        blackName.setOpaque(true);
        blackName.setVerticalTextPosition(JLabel.TOP);//文字垂直对齐方式向上
        blackName.setHorizontalTextPosition(JLabel.CENTER);//文字水平对齐方式居中
        this.add(blackName);
    }

    //创建白方姓名显示器
    public void createWhiteName() {
        //取得字体
        Font tFont = new Font("华文中宋", Font.BOLD, 25);
        whiteName.setFont(tFont);
        whiteName.setSize(150, 30);
        whiteName.setLocation(434, 72);
        whiteName.setVisible(true);
//        whiteName.setOpaque(true);
        whiteName.setVerticalTextPosition(JLabel.TOP);//文字垂直对齐方式向上
        whiteName.setHorizontalTextPosition(JLabel.CENTER);//文字水平对齐方式居中
        this.add(whiteName);

    }

    //创建重新开始键
    public void createRestart() {

        ImageIcon a = new ImageIcon("src/images/重新开始.png");
        Image b = a.getImage();
        Image c = b.getScaledInstance(150, 56, java.awt.Image.SCALE_SMOOTH);
        ImageIcon d = new ImageIcon(c);
        restart.setIcon(d);
        restart.setEnabled(true);
        restart.addMouseListener(this);

        restart.setSize(150, 56);
        restart.setLocation(532, 603);
        restart.setVisible(true);
        this.add(restart);
        restart.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                (new anniu()).start();
                GamePanel newGP = new GamePanel();
                newGP.blackname = blackname;
                newGP.whitename = whitename;
                GameSystem.frame.setPanel(newGP);
            }
        });
    }

    //创建上帝模式字样
    public void createGodMode() {
        ImageIcon a = new ImageIcon("src/images/上帝模式.png");
        Image b = a.getImage();
        Image c = b.getScaledInstance(170, 60, java.awt.Image.SCALE_SMOOTH);
        ImageIcon d = new ImageIcon(c);
        godMode.setIcon(d);
        godMode.setEnabled(true);
        godMode.addMouseListener(this);
        godMode.setSize(170, 60);
        godMode.setLocation(512, 652);
        godMode.setVisible(true);
        this.add(godMode);

        godMode.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                (new anniu()).start();
                god.setSelected(!god.isSelected());
                if (god.isSelected()) {
                    isCheatModeBlack = true;
                    isCheatModeWhite = true;
                }
                if (!god.isSelected()) {
                    isCheatModeWhite = false;
                    isCheatModeBlack = false;
                }
            }
        });
    }

    //创建返回按钮
    public void createInitial() {
        Font tFont = createFont();

        ImageIcon a = new ImageIcon("src/images/返回至主菜单.png");
        Image b = a.getImage();
        Image c = b.getScaledInstance(165, 55, java.awt.Image.SCALE_SMOOTH);
        ImageIcon d = new ImageIcon(c);
        initial.setIcon(d);
        initial.setEnabled(true);
        initial.addMouseListener(this);
        initial.setSize(165, 55);
        initial.setLocation(16, 25);
        initial.setVisible(true);
        this.add(initial);
        initial.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                (new anniu()).start();
                InitialPanel iP = new InitialPanel();
                GameSystem.frame.setPanel(iP);
            }
        });
    }

    //创建上帝模式选择器
    public void createGod() {
        Font tFont = createFont();

//        ImageIcon a = new ImageIcon("src/images/上帝模式.png");
//        Image b = a.getImage();
//        Image c = b.getScaledInstance(200,30,java.awt.Image.SCALE_SMOOTH);
//        ImageIcon d = new ImageIcon(c);
//        god.setIcon(d);
//        god.setEnabled(true);
//        god.addMouseListener(this);

//        god.setFont(tFont);
        god.setSize(170, 30);
//        god.setOpaque(true);
        god.setLocation(500, 660);
//        god.setText("上帝模式");
        god.setVisible(true);
        god.setContentAreaFilled(false);
        god.setFocusPainted(false);
        god.setBorderPainted(false);
        god.setBorder(null);
        god.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                (new anniu()).start();
                if (god.isSelected()) {
                    isCheatModeBlack = true;
                    isCheatModeWhite = true;
                }
                if (!god.isSelected()) {
                    isCheatModeWhite = false;
                    isCheatModeBlack = false;
                }
            }
        });

        this.add(god);

    }

    //创建悔棋按钮
    public void createRetract() {

        ImageIcon a = new ImageIcon("src/images/悔棋.png");
        Image b = a.getImage();
        Image c = b.getScaledInstance(100, 65, java.awt.Image.SCALE_SMOOTH);
        ImageIcon d = new ImageIcon(c);
        retract.setIcon(d);
        retract.setEnabled(true);
        retract.addMouseListener(this);
        retract.setSize(100, 65);
//        retract.setOpaque(true);
        retract.setLocation(584, 551);
        retract.setVisible(true);
        this.add(retract);

        retract.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                (new anniu()).start();
                if (ChessBoards.size() <= 1) return;
                int N = ChessBoards.size() - 1;
                ChessBoards.remove(N);
                nextTypes.remove(N);
                nextType = nextTypes.get(nextTypes.size() - 1);
                reminder.setType(nextType);
                unitize();
            }
        });
    }

    //创建保存按钮
    public void createSave() {
        ImageIcon a = new ImageIcon("src/images/存档.png");
        Image b = a.getImage();
        Image c = b.getScaledInstance(90, 58, java.awt.Image.SCALE_SMOOTH);
        ImageIcon d = new ImageIcon(c);
        save.setIcon(d);
        save.setEnabled(true);
        save.addMouseListener(this);
        save.setSize(90, 58);
        save.setLocation(598, 502);
        save.setVisible(true);
        this.add(save);

        save.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                (new anniu()).start();
                write();
                super.mouseReleased(e);

            }
        });
    }

    //根据数字棋盘同意指示器二维数组及棋子二维数组
    public void unitize() {
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                qiziss[i - 1][j - 1].setType(ChessBoards.get(ChessBoards.size() - 1)[j][i]);
                pointers[i - 1][j - 1].setType(ChessBoards.get(ChessBoards.size() - 1)[j][i]);
//                points[i - 1][j - 1].setType(possiblePlace(ChessBoards.get(ChessBoards.size() - 1),nextType)[j][i]);
            }
        }
    }

    //事件监听
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("菜单指向被点击了");
        String command = e.getActionCommand();
        System.out.println("指令" + command);
    }

    //画图
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //背景图
        g.drawImage(ImageValue.GamePanelback, 0, 0, Constants.boardWidth + Constants.marginLeft + Constants.marginRight, Constants.boardHeight + Constants.marginTop + Constants.marginBottom, null);

        //绘制网格
        drawGrid(g);

        //绘制指示器
        drawPointer(g);

        //绘制点点
        if (!isCheatModeWhite) drawPoint(g);

        //绘制棋子
        drawQizi(g);

        //绘制提醒
        reminder.draw(g);
    }

    //绘制点点
    private void drawPoint(Graphics g) {
        Point point;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                point = points[i][j];
                point.draw(g);
            }
        }
    }

    //绘制棋子
    private void drawQizi(Graphics g) {
        Qizi qizi;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                qizi = qiziss[i][j];
                if (qizi != null) {
                    qizi.draw(g);
                }
            }
        }
    }

    //绘制指示器
    private void drawPointer(Graphics g) {
        Pointer pointer;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                pointer = pointers[i][j];
                if (pointer != null) {
                    pointer.draw(g);
                }
            }
        }
    }

    //绘制网格
    public void drawGrid(Graphics g) {
        float lineWidth = 2f;
        g.setColor(Color.BLACK);
        ((Graphics2D) g).setStroke(new BasicStroke(lineWidth));
        int start = 40;
        int x1 = 40;
        int y1 = 40;
        int x2 = 460;
        int y2 = 40;
        int distance = 60;
        for (int i = 0; i < ROWS; i++) {
            y1 = i * distance + start;
            y2 = y1;
            g.drawLine(x1 + Constants.marginLeft, y1 + Constants.marginTop, x2 + Constants.marginLeft, y2 + Constants.marginTop);
        }
        y1 = 40;
        y2 = 460;
        for (int i = 0; i < COLUMNS; i++) {
            x1 = i * distance + start;
            x2 = x1;
            g.drawLine(x1 + Constants.marginLeft, y1 + Constants.marginTop, x2 + Constants.marginLeft, y2 + Constants.marginTop);
        }
    }

    //下棋后更改数字棋盘并返回更改后的棋盘二维数组
    public int[][] change(int[][] board, int x, int y, int type) {
        int n = 1;
        int color = type;
        int state[][] = new int[10][10];
        for (int i = 0; i <= 9; i++) {
            state[0][i] = 2;
            state[i][0] = 2;
            state[9][i] = 2;
            state[i][9] = 2;
        }
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                state[i][j] = board[i][j];
            }
        }
        int fx[] = {-1, 1, 0, 0, -1, -1, 1, 1};
        int fy[] = {0, 0, -1, 1, -1, 1, -1, 1};
        while (n-- > 0) {
            boolean flag = false;
            for (int i = 0; i <= 7; i++) {
                if (state[x + fx[i]][y + fy[i]] != -color) continue;
                int k = 1;
                while (state[x + k * fx[i]][y + k * fy[i]] == -color) {
                    k++;
                }
                if (state[x + k * fx[i]][y + k * fy[i]] == color) {
                    flag = true;
                    state[x][y] = color;
                    for (int j = 1; j <= k; j++) {
                        state[x + j * fx[i]][y + j * fy[i]] = color;
                    }
                }
            }
            if (flag || (type == 1 && isCheatModeWhite) || (type == -1 && isCheatModeBlack)) {
                state[x][y] = color;
            }
        }
        return state;
    }

    //从当前数字棋盘中寻找可以下的位置
    public int[][] possiblePlace(int[][] board, int type) {
        int[][] a = new int[8 + 2][8 + 2];
        int[] kong = new int[
                                                                                                                                                          5 + 2];
        boolean can = false;
        boolean isthere = false;
        int jimmy = type;
        int alpaca = -jimmy;
        int[] x = new int[64];
        int[] y = new int[64];
        int st = 1;
        int[][] ans = new int[8 + 2][8 + 2];
        for (int j = 1; j <= 8; j++) {
            for (int k = 1; k <= 8; k++) {
                a[j][k] = board[j][k];
                if (a[j][k] == jimmy) {
                    x[st] = j;
                    y[st] = k;
                    st++;
                    isthere = true;
                }
            }
        }
        if (isthere == true) {
            for (int j = 1; j < st; j++) {
                int xx = x[j];
                int yy = y[j];
                while (a[xx][yy] == jimmy) xx--;
                if (xx >= 2 && a[xx][yy] == alpaca) {
                    while (a[xx][yy] == alpaca) xx--;
                    if (xx >= 1 && a[xx][yy] == 0)
                        ans[xx][yy] = 1;
                }
                xx = x[j];
                yy = y[j];
                while (a[xx][yy] == jimmy) xx++;
                if (xx <= 7 && a[xx][yy] == alpaca) {
                    while (a[xx][yy] == alpaca) xx++;
                    if (xx <= 8 && a[xx][yy] == 0)
                        ans[xx][yy] = 1;
                }
                xx = x[j];
                yy = y[j];
                while (a[xx][yy] == jimmy) yy++;
                if (yy <= 7 && a[xx][yy] == alpaca) {
                    while (a[xx][yy] == alpaca) yy++;
                    if (yy <= 8 && a[xx][yy] == 0)
                        ans[xx][yy] = 1;
                }
                xx = x[j];
                yy = y[j];
                while (a[xx][yy] == jimmy) yy--;
                if (yy >= 2 && a[xx][yy] == alpaca) {
                    while (a[xx][yy] == alpaca) yy--;
                    if (yy >= 1 && a[xx][yy] == 0)
                        ans[xx][yy] = 1;
                }
                xx = x[j];
                yy = y[j];
                while (a[xx][yy] == jimmy) {
                    xx--;
                    yy--;
                }
                if (yy >= 2 && xx >= 2 && a[xx][yy] == alpaca) {
                    while (a[xx][yy] == alpaca) {
                        yy--;
                        xx--;
                    }
                    if (yy >= 1 && xx >= 1 && a[xx][yy] == 0)
                        ans[xx][yy] = 1;
                }
                xx = x[j];
                yy = y[j];
                while (a[xx][yy] == jimmy) {
                    xx++;
                    yy++;
                }
                if (yy <= 7 && xx <= 7 && a[xx][yy] == alpaca) {
                    while (a[xx][yy] == alpaca) {
                        yy++;
                        xx++;
                    }
                    if (yy <= 8 && xx <= 8 && a[xx][yy] == 0) {
                        ans[xx][yy] = 1;
                    }
                }
                xx = x[j];
                yy = y[j];
                while (a[xx][yy] == jimmy) {
                    xx--;
                    yy++;
                }
                if (yy <= 7 && xx >= 2 && a[xx][yy] == alpaca) {
                    while (a[xx][yy] == alpaca) {
                        yy++;
                        xx--;
                    }
                    if (yy <= 8 && xx >= 1 && a[xx][yy] == 0) {
                        ans[xx][yy] = 1;
                    }
                }
                xx = x[j];
                yy = y[j];
                while (a[xx][yy] == jimmy) {
                    xx++;
                    yy--;
                }
                if (yy >= 2 && xx <= 7 && a[xx][yy] == alpaca) {
                    while (a[xx][yy] == alpaca) {
                        yy--;
                        xx++;
                    }
                    if (yy >= 1 && xx <= 8 && a[xx][yy] == 0) {
                        ans[xx][yy] = 1;
                    }
                }
                xx = x[j];
                yy = y[j];
            }
        }
        for (int h = 1; h <= 8; h++) {
            for (int j = 1; j <= 8; j++) {
                if (ans[h][j] != 0) can = true;
            }
        }
        return ans;
    }

    //判断某方是否有地方可下
    public boolean flag(int[][] board) {
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                if (board[i][j] != 0) {
                    return true;
                }
            }
        }
        return false;
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
        if (e.getSource().equals(initial)) {//e指一个事件。e.getSource()获取事件
            //如果鼠标移入到（start）组件（图片按钮）
            initial.setEnabled(false);
        } else if (e.getSource().equals(restart)) {
            restart.setEnabled(false);
        } else if (e.getSource().equals(retract)) {
            retract.setEnabled(false);
        } else if (e.getSource().equals(save)) {
            save.setEnabled(false);
        } else if (e.getSource().equals(god)) {
            god.setEnabled(false);
        } else if (e.getSource().equals(godMode)) {
            godMode.setEnabled(false);
        } else if (e.getSource().equals(show)) {
            show.setEnabled(false);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //鼠标移出
        if (e.getSource().equals(initial)) {
            initial.setEnabled(true);
        } else if (e.getSource().equals(restart)) {
            restart.setEnabled(true);
        } else if (e.getSource().equals(retract)) {
            retract.setEnabled(true);
        } else if (e.getSource().equals(save)) {
            save.setEnabled(true);
        } else if (e.getSource().equals(god)) {
            god.setEnabled(true);
        } else if (e.getSource().equals(godMode)) {
            godMode.setEnabled(true);
        } else if (e.getSource().equals(show)) {
            show.setEnabled(true);
        }
    }

    //重写toString方法
    public String toString() {
        String str = "0" + "\n" + whitename + "\n" + blackname + "\n" + ChessBoards.size() + "\n";
        for (int[][] board : ChessBoards) {
            for (int i = 0; i <= 9; i++) {
                for (int j = 0; j <= 9; j++) {
                    str += board[i][j] + " ";
                }
            }
            str += "\n";
        }
        for (Integer a : nextTypes) {
            str += a + "\n";
        }
        return str;
    }

    public void winner(String string) {
        JOptionPane.showMessageDialog(null, string, "游戏结束", JOptionPane.PLAIN_MESSAGE);

//        String path = String.format("C:\\Users\\ASUS\\Player\\date", filename);    }
    }

    //    //写文件方法
    public void write() {
        String filename = JOptionPane.showInputDialog(this, "input the name here");
        String path = String.format("date\\%s.txt", filename);
        File file = new File(path);
        if (file.exists()) JOptionPane.showMessageDialog(null, "文件已存在", "错误", JOptionPane.PLAIN_MESSAGE);
        ;
        try {
            file.createNewFile();
        } catch (
                IOException ioException) {
            ioException.printStackTrace();
        }

        FileWriter fw = null;
        try {
            fw = new FileWriter(file, true);
        } catch (
                IOException ioException) {
            ioException.printStackTrace();
        }

        BufferedWriter bw = new BufferedWriter(fw);

        try {
            bw.write(this.toString());
        } catch (
                IOException ioException) {
            ioException.printStackTrace();
        }

        try {
            bw.flush();
        } catch (
                IOException ioException) {
            ioException.printStackTrace();
        }

        try {
            bw.close();
        } catch (
                IOException ioException) {
            ioException.printStackTrace();
        }

        try {
            fw.close();
        } catch (
                IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void reading(String string) {
        Scanner input = new Scanner(string);
        String str = input.nextLine();
        ArrayList<int[][]> chessboards = new ArrayList<>();
        ArrayList<Integer> nexttypes = new ArrayList<>();
        this.whitename = input.nextLine();
        this.blackname = input.nextLine();
        int length = input.nextInt();
        for (int i = 0; i < length; i++) {
            int[][] board = new int[10][10];
            for (int j = 0; j <= 9; j++) {
                for (int k = 0; k <= 9; k++) {
                    board[j][k] = input.nextInt();
                }
            }
            chessboards.add(board);
        }
        for (int i = 0; i < length; i++) {
            nexttypes.add(input.nextInt());
        }

        this.ChessBoards = chessboards;
        this.nextTypes = nexttypes;
        this.nextType = nexttypes.get(nexttypes.size() - 1);
        this.reminder.setType(this.nextType);
        unitize();



    }

    public ArrayList<int[][]> getChessBoards() {
        return ChessBoards;
    }

    public void setChessBoards(ArrayList<int[][]> chessBoards) {
        ChessBoards = chessBoards;
    }

    public ArrayList<Integer> getNextTypes() {
        return nextTypes;
    }

    public void setNextTypes(ArrayList<Integer> nextTypes) {
        this.nextTypes = nextTypes;
    }

    public int getNextType() {
        return nextType;
    }

    public void setNextType(int nextType) {
        this.nextType = nextType;
    }
}
