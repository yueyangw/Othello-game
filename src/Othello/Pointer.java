package Othello;

import java.awt.*;

//指示器类
public class Pointer {
    private int i = 0;//二维数组下标
    private int j = 0;//二维数组下标
    private int x = 0;//x坐标
    private int y = 0;//y坐标
    private int h = 50;//指示器的高
    private boolean isShow = false;//是否展示
    private int type = 0;//0 空 1 白棋 -1 黑棋

    public Pointer(int i, int j, int x, int y){
        this.i = i;
        this.j = j;
        this.x = x;
        this.y = y;
    }

    //绘制指示器
    public void draw(Graphics g){
        g.setColor((new Color(255,0,0)));
        if(isShow) {
            float lineWidth = 3.0f;
            ((Graphics2D)g).setStroke(new BasicStroke(lineWidth));
            g.drawLine(x - h / 2 + Constants.marginLeft, y - h / 2 + Constants.marginTop,x - h / 4 + Constants.marginLeft ,y - h / 2 + Constants.marginTop);
            g.drawLine(x + h / 4 + Constants.marginLeft, y - h / 2 + Constants.marginTop,x + h / 2 + Constants.marginLeft ,y - h / 2 + Constants.marginTop);
            g.drawLine(x + h / 2 + Constants.marginLeft, y - h / 2 + Constants.marginTop,x + h / 2 + Constants.marginLeft ,y - h / 4 + Constants.marginTop);
            g.drawLine(x + h / 2 + Constants.marginLeft, y + h / 4 + Constants.marginTop,x + h / 2 + Constants.marginLeft ,y + h / 2 + Constants.marginTop);
            g.drawLine(x + h / 2 + Constants.marginLeft, y + h / 2 + Constants.marginTop,x + h / 4 + Constants.marginLeft ,y + h / 2 + Constants.marginTop);
            g.drawLine(x - h / 4 + Constants.marginLeft, y + h / 2 + Constants.marginTop,x - h / 2 + Constants.marginLeft ,y + h / 2 + Constants.marginTop);
            g.drawLine(x - h / 2 + Constants.marginLeft, y + h / 2 + Constants.marginTop,x - h / 2 + Constants.marginLeft ,y + h / 4 + Constants.marginTop);
            g.drawLine(x - h / 2 + Constants.marginLeft, y - h / 4 + Constants.marginTop,x - h / 2 + Constants.marginLeft ,y - h / 2 + Constants.marginTop);
        }
    }

    //判断是否在指示器范围内
    public boolean isPoint(int x, int y){
        int x1 = this.x - h/2;
        int y1 = this.y - h/2;
        int x2 = this.x + h/2;
        int y2 = this.y + h/2;

        return x>x1&&y>y1&&x<x2&&y<y2;
    }

    public boolean isShow(){
        return isShow;
    }

    public void setShow(boolean isShow){
        this.isShow = isShow;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    //public boolean

}
