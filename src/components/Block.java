package components;

import game_stuff.GamePanel;

import java.util.Random;

public class Block {

    final Integer MAX_OBSTACLE_WIDTH = 50;
    final Integer MAX_OBSTACLE_HEIGHT = 100;
    Integer xAxis;

    private final Integer HEIGHT;
    private final Integer WIDTH;

    public Block() {
        Random random = new Random();
        this.WIDTH = random.nextInt(MAX_OBSTACLE_WIDTH) + 20;
        this.HEIGHT = random.nextInt(MAX_OBSTACLE_HEIGHT) + 30;
        this.xAxis = GamePanel.SCREEN_WIDTH;
    }

    public Integer getHeight() {
        return this.HEIGHT;
    }

    public Integer getWidth() {
        return this.WIDTH;
    }

    public Integer getxAxis(){
        return this.xAxis;
    }

    public void moveForward(){
        this.xAxis -= 2;
    }
}
