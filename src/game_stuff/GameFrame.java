package game_stuff;

import javax.swing.*;

public class GameFrame extends JFrame {

    //The game's frame
    public GameFrame() {
        this.add(new GamePanel());
        this.setResizable(false);
        this.setVisible(true);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
