package Othello;
import javax.swing.*;

public class MainFrame extends JFrame {
    public MainFrame(){
        setTitle("Othello-黑白棋");//设置标题
        setSize(Constants.boardWidth + Constants.marginLeft + Constants.marginRight + Constants.insetHorizontal,
                Constants.boardHeight + Constants.marginTop + Constants.marginBottom + Constants.insetVertical);//设置窗口大小
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);//居中
        setResizable(false);//不允许变大变小
        //setVisible(true);//显示窗体
    }

    //加载画面
    public void setPanel(JPanel p) {
        this.setContentPane(p);
        this.repaint();
        this.getContentPane().repaint();
    }
}
