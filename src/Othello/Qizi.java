package Othello;

import java.awt.*;

public class Qizi {

    private int i = 0;//二维数组下标
    private int j = 0;//二维数组下标
    private int x = 0;//x坐标
    private int y = 0;//y坐标
    private int h = 50;//高
    private int type = 1;//1表示白棋 -1表示黑棋
    private int xx = 0;//棋盘坐标
    private int yy = 0;//棋盘坐标
    //构造方法
    public Qizi(int i, int j, int x, int y, int type){
        this.i = i;
        this.j = j;
        this.x = x;
        this.y = y;
        this.type = type;
        this.xx = ( x - 40 ) / 60 + 1;
        this.yy = ( y - 40 ) / 60 + 1;
    }

    //画棋子
    public void draw(Graphics g){
        if(type == 1){
            g.drawImage(ImageValue.whiteImage, x-h/2+Constants.marginLeft, y-h/2+Constants.marginTop, h, h, null);
        }else if(type == -1){
            g.drawImage(ImageValue.blackImage, x-h/2+Constants.marginLeft, y-h/2+Constants.marginTop, h, h, null);
        }
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
