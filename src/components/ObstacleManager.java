package components;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import static game_stuff.GamePanel.GROUND_HEIGHT;
import static game_stuff.GamePanel.SCREEN_WIDTH;

public class ObstacleManager {

    private final Integer OBSTACLE_WIDTH_RANGE = 50;
    private final Integer OBSTACLE_HEIGHT_RANGE = 100;
    private final Integer MINIMUM_HEIGHT = 20;
    private final Integer MINIMUM_WIDTH = 20;

    private ArrayList<Rectangle> obstacles = new ArrayList<>();
    private Iterator<Rectangle> iterator = obstacles.iterator();
    private Integer score = 0;

    public void maintainObstacles(Graphics graphics) {
        graphics.setColor(Color.darkGray);
        while (iterator.hasNext()) {
            Rectangle obstacle = iterator.next();
            moveObstacle(obstacle);
            graphics.fillRect(obstacle.getBounds().x, obstacle.getBounds().y, obstacle.getBounds().width, obstacle.getBounds().height);
        }
    }

    public Rectangle getFirstObstacle(){
        if(!obstacles.isEmpty()){
            return obstacles.get(0);
        }

        return null;
    }

    private void moveObstacle(Rectangle obstacle){
        if (obstacle.x <= -obstacle.width) {
            iterator.remove();
            score++;
        }

        obstacle.x -= 2;
    }

    public void setNewObstacle() {
        Random random = new Random();
        int obsHeight = random.nextInt(OBSTACLE_HEIGHT_RANGE) + MINIMUM_HEIGHT;
        int obsWidth = random.nextInt(OBSTACLE_WIDTH_RANGE) + MINIMUM_WIDTH;
        Rectangle obs = new Rectangle(SCREEN_WIDTH,GROUND_HEIGHT - obsHeight,obsWidth,obsHeight);

        if (random.nextInt(150) > 148) {
            if (obstacles.isEmpty()) {
                obstacles.add(obs);
            } else {
                Rectangle lastCurrentObstacle = obstacles.get(obstacles.size() - 1);
                if (lastCurrentObstacle.x < (int) (SCREEN_WIDTH * 0.85)) {
                    obstacles.add(obs);
                }
            }
        }

        this.iterator = obstacles.iterator();
    }

    public Integer getScore(){
        return this.score;
    }

    public void reset(){
        this.score = 0;
        this.obstacles = new ArrayList<>();
    }
}
