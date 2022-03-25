package Othello;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ClassicChoose extends ChoosePanel implements MouseListener {
    public ClassicChoose(){
        this.setLayout(null);

        //创建黑方姓名输入框
        createBlackField();

        //创建白方姓名输入框
        createWhiteField();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        //背景图
//        g.fillRect(1,1,200,200);
        g.drawImage(ImageValue.ChooseBackground, 0, 0, Constants.boardWidth + Constants.marginLeft + Constants.marginRight, Constants.boardHeight + Constants.marginTop + Constants.marginBottom, null);
    }

    public void createWhiteField() {
        Font tFont = createFont();
        whiteTextField.setFont(tFont);
        whiteTextField.setEditable(true); // 设置输入框允许编辑
        whiteTextField.setColumns(11); // 设置输入框的长度为11个字符
        whiteTextField.setBounds(280, 329, 300, 50);
        whiteTextField.setVisible(true);
        whiteTextField.setOpaque(false);
        whiteTextField.setBorder(null);
        this.add(whiteTextField); // 在面板上添加单行输入框
    }

    public void createBlackField() {
        Font tFont = createFont();
        blackTextField.setFont(tFont);
        blackTextField.setEditable(true); // 设置输入框允许编辑
        blackTextField.setColumns(11); // 设置输入框的长度为11个字符
        blackTextField.setBounds(280, 242, 300, 50);
        blackTextField.setOpaque(false);
        blackTextField.setBorder(null);
        blackTextField.setVisible(true);
        this.add(blackTextField); // 在面板上添加单行输入框
    }

    public void createYes(){
        yes.setSize(130, 80);
        yes.setLocation(283, 466);
        ImageIcon a = new ImageIcon("src/images/确定.png");
        Image b = a.getImage();
        Image c = b.getScaledInstance(130,80,java.awt.Image.SCALE_SMOOTH);
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
                GamePanel newGP = new GamePanel();
                newGP.blackname = blackTextField.getText();
                newGP.whitename = whiteTextField.getText();
                newGP.blackName.setText(newGP.blackname);
                newGP.whiteName.setText(newGP.whitename);
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
