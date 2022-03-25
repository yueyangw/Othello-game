import Othello.*;

public final class Main {
    private static void init() {
        ImageValue.init();
    }

    public static void main(String[] args) {
        init();

        GameSystem.frame.setVisible(true);
        GameSystem.frame.setPanel(new InitialPanel());
//        GameSystem.frame.setPanel(new GamePanel());
//        GameSystem.frame.setPanel(new ClassicChoose());
//        GameSystem.frame.setPanel(new ComputerGame());
    }
}
