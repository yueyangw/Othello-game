package Othello;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ComputerChoose extends ChoosePanel implements ActionListener, MouseListener {
    public JCheckBox bai = new JCheckBox();
    public JCheckBox hei = new JCheckBox();

    public ComputerChoose(){
        this.setLayout(null);

        createWhiteField();

        createBlackField();

        createHei();
        createBai();


        //事件重绘
        new Timer(delay, taskPerformer).start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        //背景图
        g.drawImage(ImageValue.renjichoose, 0, 0, Constants.boardWidth + Constants.marginLeft + Constants.marginRight, Constants.boardHeight + Constants.marginTop + Constants.marginBottom, null);
    }
    ActionListener taskPerformer = new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            rep();
            if(bai.isSelected()){
                hei.setSelected(false);
            }else if(hei.isSelected()){
                bai.setSelected(false);
            }
        }
    };

    int delay = 100; //milliseconds 刷新间隔

    private void rep() {
        repaint();
    }

    public void createWhiteField() {
        Font tFont = createFont();
        whiteTextField.setFont(tFont);
        whiteTextField.setEditable(true); // 设置输入框允许编辑
        whiteTextField.setColumns(11); // 设置输入框的长度为11个字符
        whiteTextField.setBounds(280, 242-50-3, 300, 50);
        whiteTextField.setVisible(true);
        whiteTextField.setOpaque(false);
        whiteTextField.setBorder(null);
        this.add(whiteTextField); // 在面板上添加单行输入框
    }

    public void createBlackField() {
        Font tFont = createFont();
        blackTextField.setFont(tFont);
        blackTextField.setEditable(true); // 设置输入框允许编辑
        blackTextField.setColumns(2); // 设置输入框的长度为11个字符
        blackTextField.setBounds(445, 360, 50, 50);
        blackTextField.setOpaque(false);
        blackTextField.setBorder(null);
        blackTextField.setVisible(true);
        this.add(blackTextField); // 在面板上添加单行输入框
    }

    public void createBai(){
        Font tFont = createFont();
        bai.setFont(tFont);
        bai.setBounds(400,278,100,50);
        bai.setText("白方");
        bai.setVisible(true);
        bai.setOpaque(false);
        bai.setBorder(null);
        this.add(bai); // 在面板上添加单行输入框
    }

    public void createHei(){
        Font tFont = createFont();
        hei.setFont(tFont);
        hei.setBounds(300,278,100,50);
        hei.setSelected(true);
        hei.setText("黑方");
        hei.setVisible(true);
        hei.setOpaque(false);
        hei.setBorder(null);
        this.add(hei); // 在面板上添加单行输入框
    }

    public void createYes(){
        yes.setSize(130, 80);
        yes.setLocation(280, 511);
        ImageIcon a = new ImageIcon("src/images/确定.png");
        Image b = a.getImage();
        Image c = b.getScaledInstance(135,90,java.awt.Image.SCALE_SMOOTH);
        ImageIcon d = new ImageIcon(c);
        yes.setIcon(d);
        yes.setEnabled(true);
        yes.addMouseListener(this);
        yes.setVisible(true);

        yes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                (new start()).start();
                ComputerGame newGP = new ComputerGame();
//                newGP.blackname = blackTextField.getText();
//                newGP.whitename = whiteTextField.getText();
//                newGP.blackName.setText(newGP.blackname);
//                newGP.whiteName.setText(newGP.whitename);

                newGP.difficulty = Integer.parseInt(blackTextField.getText());
                newGP.AIPlayer2 =new MCTS(newGP.difficulty, 8, 10000000, 19260817);
                if(hei.isSelected()){
                    newGP.manType = -1;
                    newGP.blackname = whiteTextField.getText();
                    newGP.blackName.setText(newGP.blackname);
                    newGP.whitename = "computer";
                    newGP.whiteName.setText("computer");
                }else{
                    newGP.manType = 1;
                    newGP.whitename = whiteTextField.getText();
                    newGP.whiteName.setText(newGP.whitename);
                    newGP.blackname = "computer";
                    newGP.blackName.setText("computer");
                }
                GameSystem.frame.setPanel(newGP);
            }
        });

        this.add(yes);
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
        if (e.getSource().equals(yes)) {//e指一个事件。e.getSource()获取事件
            //如果鼠标移入到（start）组件（图片按钮）
            yes.setEnabled(false);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource().equals(yes)) {
            yes.setEnabled(true);
        }
    }


}
