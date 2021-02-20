import java.util.Random;

public class Obstacle {

    final Integer MAX_OBSTACLE_WIDTH = 50;
    final Integer MAX_OBSTACLE_HEIGHT = 100;
    Integer xAxis;

    private final Integer HEIGHT;
    private final Integer WIDTH;

    public Obstacle() {
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
}
