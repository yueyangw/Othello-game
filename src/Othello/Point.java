package Othello;

import java.awt.*;

public class Point {
    private int i = 0;//二维数组下标
    private int j = 0;//二维数组下标
    private int x = 0;//x坐标
    private int y = 0;//y坐标
    private int type = 0;
    private int xx = 0;//棋盘坐标
    private int yy = 0;//棋盘坐标
    //构造方法
    public Point(int i, int j, int x, int y, int type){
        this.i = i;
        this.j = j;
        this.x = x;
        this.y = y;
        this.xx = ( x - 40 ) / 60 + 1;
        this.yy = ( y - 40 ) / 60 + 1;
        this.type = type;
    }

    public void draw(Graphics g){
        g.setColor(Color.black);
        if(type != 0){
            g.fillArc(x-5,y-5,10,10,0,360);
        }
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getXx() {
        return xx;
    }

    public void setXx(int xx) {
        this.xx = xx;
    }

    public int getYy() {
        return yy;
    }

    public void setYy(int yy) {
        this.yy = yy;
    }
}
