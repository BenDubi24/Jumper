package game_stuff;

import components.Jumper;
import components.ObstacleManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel implements ActionListener {

    public static final Integer SCREEN_WIDTH = 1000;
    public static final Integer SCREEN_HEIGHT = 500;
    public static final Integer TIME_DELAY = 5;
    public static final Integer GROUND_HEIGHT = 450;

    private Jumper jumper;
    private Timer timer;
    private ObstacleManager obstacleManager;
    private boolean isGameOver;
    private int score;
    private int highScore = score;

    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.white);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        this.isGameOver = false;
        this.jumper = new Jumper();
        this.obstacleManager = new ObstacleManager();
        this.score = obstacleManager.getScore();
        startGame();
    }

    public void actionPerformed(ActionEvent e) {
        if (!isGameOver) {
            moveForward();
            repaint();
        } else {
            finishGame();
        }
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        paintJumper(graphics);
        paintScore(graphics);
        paintGround(graphics);
        this.obstacleManager.maintainObstacles(graphics);
    }

    private void startGame() {
        this.timer = new Timer(TIME_DELAY, this);
        this.timer.start();
        this.obstacleManager.setNewObstacle();
    }

    private void paintGround(Graphics graphics) {
        graphics.setColor(Color.black);
        graphics.fillRect(0, GROUND_HEIGHT, SCREEN_WIDTH, SCREEN_HEIGHT - GROUND_HEIGHT);
    }

    private void paintJumper(Graphics graphics) {
        if (jumper.isInTheAir()) {
            jumper.keepJumping();
        }

        graphics.setColor(Color.orange);
        graphics.fillOval(jumper.getX(), jumper.getY() - jumper.getHeight(), jumper.getWidth(), jumper.getHeight());
    }

    public Integer placement(String message, FontMetrics metrics) {
        return placement(message, metrics, 2.0);
    }

    public Integer placement(String message, FontMetrics metrics, double ratio) {
        return (int) ((SCREEN_WIDTH - metrics.stringWidth(message)) / ratio);
    }

    private void paintScore(Graphics graphics) {
        final String scoreMessage;
        final FontMetrics metrics = getFontMetrics(graphics.getFont());
        final String highScoreMessage = " Highest score : " + highScore;

        double ratio = 1.1;
        Integer height = 25;
        graphics.setColor(Color.blue);
        graphics.setFont(new Font("F", Font.ITALIC, 20));

        if (!isGameOver) {
            scoreMessage = "Score:" + obstacleManager.getScore();
        } else {
            ratio = 2.0;
            height = 75;
            scoreMessage = "Game over! Final score : " + obstacleManager.getScore();
            String replayMessage = "Press Ctrl to replay";

            graphics.drawString(replayMessage, placement(replayMessage, metrics), 100);
        }

        graphics.drawString(highScoreMessage, placement(highScoreMessage, metrics, ratio), height);
        graphics.drawString(scoreMessage, placement(scoreMessage, metrics), 50);
    }

    private void moveForward() {
        this.obstacleManager.setNewObstacle();
        checkForCollisions();
    }

    private void checkForCollisions() {
        Rectangle firstRect = this.obstacleManager.getFirstObstacle();
        if (firstRect != null) {
            if ((jumper.getY() >= GROUND_HEIGHT - firstRect.height) &&
                    (firstRect.x <= jumper.getX() + jumper.getWidth()) &&
                    (firstRect.x >= jumper.getX())) {
                if (highScore < obstacleManager.getScore()) highScore = obstacleManager.getScore();
                isGameOver = true;
            }
        }
    }

    private void finishGame() {
        timer.stop();

        if (score > highScore) {
            highScore = score;
        }
    }

    private void restart() {
        jumper = new Jumper();
        obstacleManager.reset();
        isGameOver = false;
        timer.restart();
    }

    private class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_UP && !jumper.isInTheAir()) {
                jumper.setIsInTheAirTrue();
            }

            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                if (!jumper.isInTheAir()) {
                    jumper.squash();
                } else {
                    jumper.fall();
                }
            }

            if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
                if (isGameOver) {
                    restart();
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (jumper.isSquashed()) {
                jumper.unsquash();
            }
        }
    }
}
