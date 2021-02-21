package game_stuff;

import components.Block;
import components.Jumper;

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

    public static final Integer SCREEN_WIDTH = 1000;
    public static final Integer SCREEN_HEIGHT = 500;
    public static final Integer TIME_DELAY = 5;
    public static final Integer GROUND_HEIGHT = 450;

    private Jumper jumper = new Jumper();
    private ArrayList<Block> obstacles = new ArrayList<>();
    private Iterator<Block> iterator;
    private Timer timer;
    private boolean isLost;
    private int score = 0;
    private int highScore = score;

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
            finishGame();
        }
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        paintObstacles(graphics);
        paintJumper(graphics);
        paintScore(graphics);
    }

    private void startGame() {
        this.timer = new Timer(TIME_DELAY, this);
        this.timer.start();
        setNewObstacle();
    }

    private void paintObstacles(Graphics graphics) {
        graphics.setColor(Color.darkGray);
        while (iterator.hasNext()) {

            Block obstacle = iterator.next();
            if (obstacle.getxAxis() <= -obstacle.getWidth()) {
                iterator.remove();
                score++;
            }

            obstacle.moveForward();
            graphics.fillRect(obstacle.getxAxis(), GamePanel.GROUND_HEIGHT - obstacle.getHeight(), obstacle.getWidth(), obstacle.getHeight());
        }
    }

    private void paintJumper(Graphics graphics) {
        if(jumper.isInTheAir()){
            jumper.jump();
        }

        graphics.setColor(Color.orange);
        graphics.fillOval(Jumper.X_AXIS_PLACEMENT, jumper.getCurrentHeightRelativeToGround() - jumper.getBallHeight(), jumper.getBallWidth(), jumper.getBallHeight());
    }

    public Integer placement(String message, FontMetrics metrics){
        return placement(message,metrics,2.0);
    }

    public Integer placement(String message, FontMetrics metrics, double ratio){
        return (int)((SCREEN_WIDTH - metrics.stringWidth(message))/ratio);
    }

    private void paintScore(Graphics graphics){
        final String scoreMessage;
        final FontMetrics metrics = getFontMetrics(graphics.getFont());
        final String highScoreMessage = " Highest score : " + highScore;

        double ratio = 1.1;
        Integer height = 25;
        graphics.setColor(Color.blue);
        graphics.setFont(new Font("F",Font.ITALIC,20));

        if(!isLost){
            scoreMessage = "Score:" + score;
        }

        else {
            ratio = 2.0;
            height = 75;
            scoreMessage = "Game over! Final score : " + score;
            String replayMessage = "Press Ctrl to replay";

            graphics.drawString(replayMessage,placement(replayMessage , metrics), 100);
        }

        graphics.drawString(highScoreMessage,placement(highScoreMessage, metrics,ratio), height);
        graphics.drawString(scoreMessage, placement(scoreMessage, metrics), 50);
    }

    private void moveForward() {
        setNewObstacle();
        checkCollisions();
    }

    private void checkCollisions() {
        if (!obstacles.isEmpty()) {
            Block obs = obstacles.get(0);

            if ((jumper.getCurrentHeightRelativeToGround() >= GROUND_HEIGHT - obs.getHeight()) &&
                    (obs.getxAxis() <= Jumper.X_AXIS_PLACEMENT + jumper.getBallWidth()) &&
                    (obs.getxAxis() >= Jumper.X_AXIS_PLACEMENT)) {
                isLost = true;
            }
        }
    }

    private void setNewObstacle() {
        Random random = new Random();
        Block obs = new Block();

        if (random.nextInt(150) > 148) {
            if (obstacles.isEmpty()) {
                obstacles.add(obs);
            } else {
                Block lastCurrentObstacle = obstacles.get(obstacles.size() - 1);
                if (lastCurrentObstacle.getxAxis() < (int) (SCREEN_WIDTH * 0.85)) {
                    obstacles.add(obs);
                }
            }
        }

        this.iterator = obstacles.iterator();
    }

    private void finishGame(){
        timer.stop();

        if(score > highScore){
            highScore = score;
        }
    }

    private void restart(){
        jumper = new Jumper();
        obstacles = new ArrayList<>();
        score = 0;
        isLost = false;
        timer.restart();
    }

    private class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_UP && !jumper.isInTheAir()) {
                jumper.setJumping();
            }

            if(e.getKeyCode() == KeyEvent.VK_DOWN){
                if(!jumper.isInTheAir()){
                    jumper.squash();
                }

                else{
                    jumper.fall();
                }
            }

            if(e.getKeyCode() == KeyEvent.VK_CONTROL){
                if(isLost){
                    restart();
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e){
            if(jumper.isSquashed()){
                jumper.unsquash();
            }
        }
    }
}
