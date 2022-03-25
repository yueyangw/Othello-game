package Othello;

import java.util.ArrayList;

public class Replace extends Thread{
    @Override
    public void run() {
        super.run();
        GamePanel gp = (GamePanel) GameSystem.frame.getContentPane();
        ArrayList<int[][]> boardsList = new ArrayList<>(gp.getChessBoards());
        ArrayList<Integer> nexttypeList = new ArrayList<>(gp.getNextTypes());
        if (boardsList.size() <= 1) return;
        for(int i = boardsList.size(); i > 1; i--) {
            gp.getChessBoards().remove(i-1);
            gp.getNextTypes().remove(i-1);
        }
        System.out.println(gp.getChessBoards().size());
        gp.nextType = gp.nextTypes.get(gp.nextTypes.size() - 1);
        gp.reminder.setType(gp.nextType);
        gp.unitize();
        gp.repaint();
        for(int i = 1; i <boardsList.size(); i++){
            try {
                System.out.println(22);
                gp.getChessBoards().add(boardsList.get(i));
                gp.getNextTypes().add(nexttypeList.get(i));
                sleep(500);
                gp.nextType = nexttypeList.get(i);
                gp.reminder.setType(gp.nextType);
                gp.unitize();
                gp.repaint();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
