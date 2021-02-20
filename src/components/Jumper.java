package components;

import game_stuff.GamePanel;

public class Jumper {

    public static final Integer HEIGHT = 30;
    public static final Integer WIDTH = 30;
    public static final Integer X_AXIS_PLACEMENT = 20;
    private final Integer INITIAL_JUMPING_SPEED = 6;
    private final Double GRAVITY_ACCELERATION = -0.12;
    private final Integer INITIAL_HEIGHT_RELATIVE_TO_GROUND = GamePanel.GROUND_HEIGHT;

    private Integer height = HEIGHT;
    private Integer currentHeight;
    private Integer timeUnit = 0;
    private Boolean isJumping;
    private Integer jumpingSpeed = INITIAL_JUMPING_SPEED;

    public Jumper() {
        this.currentHeight = INITIAL_HEIGHT_RELATIVE_TO_GROUND;
        this.isJumping = false;
    }

    public void jump() {
        if (this.currentHeight > INITIAL_HEIGHT_RELATIVE_TO_GROUND) {
            this.currentHeight = INITIAL_HEIGHT_RELATIVE_TO_GROUND;
            isJumping = false;
            timeUnit = 0;
            jumpingSpeed = INITIAL_JUMPING_SPEED;

        } else {
            this.currentHeight -= (int)(jumpingSpeed + timeUnit * GRAVITY_ACCELERATION);
            timeUnit++;
        }
    }

    public boolean isJumping(){
        return this.isJumping;
    }

    public void setJumping(Boolean isJumping){
        this.isJumping = isJumping;
    }

    public Integer getCurrentHeightRelativeToGround(){
        return this.currentHeight;
    }

    public Integer getBallHeight(){
        return this.height;
    }

    public void squash(){
        this.height = HEIGHT/2;
        this.jumpingSpeed *= 2;
    }

    public void unsquash(){
        this.height = HEIGHT;
    }
}
