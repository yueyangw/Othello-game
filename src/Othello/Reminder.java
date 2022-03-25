package Othello;

import java.awt.*;

public class Reminder {
    private int type = -1;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Reminder(int type){
        this.type = type;
    }

    public void draw(Graphics g){
        if(type == 1){
            g.drawImage(ImageValue.reminder, 415, -10, 100, 100, null);
        }else if(type == -1){
            g.drawImage(ImageValue.reminder, 177, 610, 100, 100, null);
        }
    }

}
