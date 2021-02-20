import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    static final Integer SCREEN_WIDTH = 1000;
    static final Integer SCREEN_HEIGHT = 500;
    static final Integer TIME_DELAY = 5;
    static final Integer GROUND_HEIGHT = 450;
    private Jumper jumper = new Jumper();
    private ArrayList<Obstacle> obstacles = new ArrayList<>();
    private Iterator<Obstacle> iterator;
    private Timer timer;
    private boolean isLost;
    private int score = 0;

    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.white);
        this.setFocusable(true);
        this.isLost = false;
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void actionPerformed(ActionEvent e) {
        if (!isLost) {
            moveForward();
            repaint();
        }

        else{
            timer.stop();
        }
    }

    public void startGame() {
        this.timer = new Timer(TIME_DELAY, this);
        this.timer.start();
        setNewObstacle();
        this.iterator = obstacles.iterator();
        moveForward();
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        paintObstacles(graphics);
        paintJumper(graphics);
        paintScore(graphics);
    }

    public void paintObstacles(Graphics graphics) {
        graphics.setColor(Color.darkGray);
        while (iterator.hasNext()) {

            Obstacle obstacle = iterator.next();
            if (obstacle.xAxis <= 0) {
                iterator.remove();
                score++;
            }

            obstacle.xAxis -= 2;
            graphics.fillRect(obstacle.xAxis, GamePanel.GROUND_HEIGHT - obstacle.getHeight(), obstacle.getWidth(), obstacle.getHeight());
        }
    }

    public void paintJumper(Graphics graphics) {
        if(jumper.isJumping){
            jumper.jump();
        }

        graphics.setColor(Color.orange);
        graphics.fillOval(jumper.X_AXIS_PLACEMENT, jumper.currentHeight - jumper.HEIGHT, jumper.WIDTH, jumper.HEIGHT);
    }

    public void paintScore(Graphics graphics){
        String scoreMessage;
        if(!isLost){
            scoreMessage = "Score:" + score;
        }

        else {
            scoreMessage = "Game over! Final score : " + score;
        }

        graphics.setColor(Color.blue);
        graphics.setFont(new Font(scoreMessage,Font.ITALIC,20));
        FontMetrics metrics = getFontMetrics(graphics.getFont());
        graphics.drawString(scoreMessage,
                (SCREEN_WIDTH - metrics.stringWidth(scoreMessage))/2, 50);
    }

    public void moveForward() {
        setNewObstacle();
        checkCollisions();
    }

    public void checkCollisions() {
        if (!obstacles.isEmpty()) {
            Obstacle obs = obstacles.get(0);

            if ((jumper.currentHeight >= GROUND_HEIGHT - obs.getHeight()) &&
                    (obs.xAxis <= jumper.X_AXIS_PLACEMENT + jumper.WIDTH)) {
                isLost = true;
            }
        }
    }

    public void setNewObstacle() {
        Random random = new Random();
        Obstacle obs = new Obstacle();

        if (random.nextInt(150) > 148) {
            if (obstacles.isEmpty()) {
                obstacles.add(obs);
            } else {
                Obstacle lastCurrentObstacle = obstacles.get(obstacles.size() - 1);
                if (lastCurrentObstacle.xAxis < (int) (SCREEN_WIDTH * 0.85)) {
                    obstacles.add(obs);
                }
            }
        }

        iterator = obstacles.iterator();
    }

    public void restart(){
        jumper = new Jumper();
        obstacles = new ArrayList<>();
        score = 0;
        isLost = false;
        timer.restart();
    }

    private class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_UP && !jumper.isJumping) {
                jumper.isJumping = true;
            }

            if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                if(isLost){
                    restart();
                }
            }
        }
    }
}
