package Othello;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ChoosePanel extends JPanel implements ActionListener {
    public JLabel blackName = new JLabel();//请输入黑方姓名
    public JLabel whiteName = new JLabel();//请输入白方姓名
    public JTextField whiteTextField = new JTextField();//白方姓名输入框
    public JTextField blackTextField = new JTextField();//黑方姓名输入框
    public JLabel chooseColor = new JLabel();//请选择您的阵营
    public JLabel yes = new JLabel();//确定


    public ChoosePanel(){
        //创建panel大小
        this.setSize(Constants.marginLeft + Constants.boardWidth + Constants.marginRight,
                Constants.marginTop + Constants.boardHeight + Constants.marginBottom);
        this.setOpaque(false);

        //创建字体
        createFont();

        //创建确定“按钮”
        createYes();

        //事件重绘
        new Timer(delay, taskPerformer).start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        //背景图
//        g.drawImage(ImageValue.background, 0, 0, Constants.boardWidth + Constants.marginLeft + Constants.marginRight, Constants.boardHeight + Constants.marginTop + Constants.marginBottom, null);
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

    //创建字体
    public Font createFont(){
        return new Font("华文中宋",Font.BOLD,30);
    }

    public void createBlackName(){
        Font tFont = createFont();
        blackName.setText("请输入黑方姓名");
        blackName.setSize(300,50);
        blackName.setLocation(100,100);
        blackName.setFont(tFont);
        blackName.setVisible(true);
        blackName.setOpaque(true);
        this.add(blackName);
        System.out.println(this.getComponentCount());
    }

    public void createWhiteName(){
        Font tFont = createFont();
        whiteName.setText("请输入白方姓名");
        whiteName.setSize(300,50);
        whiteName.setLocation(100,200);
        whiteName.setFont(tFont);
        whiteName.setVisible(true);
        whiteName.setOpaque(true);
        this.add(whiteName);
    }

    public void createWhiteField() {
        Font tFont = createFont();
        whiteTextField.setFont(tFont);
        whiteTextField.setEditable(true); // 设置输入框允许编辑
        whiteTextField.setColumns(11); // 设置输入框的长度为11个字符
        whiteTextField.setLocation(100,300);
        whiteTextField.setVisible(true);
        whiteTextField.setOpaque(true);
        whiteTextField.setBorder(null);
        this.add(whiteTextField); // 在面板上添加单行输入框
    }

    public void createBlackField() {
        Font tFont = createFont();
        blackTextField.setEditable(true); // 设置输入框允许编辑
        blackTextField.setColumns(11); // 设置输入框的长度为11个字符
        blackTextField.setLocation(100,400);
        blackTextField.setOpaque(true);
        blackTextField.setBorder(null);
        blackTextField.setVisible(true);
        this.add(blackTextField); // 在面板上添加单行输入框
    }

    public void createYes(){
        //取得字体
        Font tFont = createFont();

        yes.setFont(tFont);
        yes.setSize(100,50);
        yes.setText("确认");
        yes.setVisible(true);
        yes.setOpaque(true);

        yes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Audio.anniu();
                GamePanel newGP = new GamePanel();
                newGP.blackName.setText(blackTextField.getText());
                newGP.whiteName.setText(whiteTextField.getText());
                GameSystem.frame.setPanel(newGP);
            }
        });

        this.add(yes);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}