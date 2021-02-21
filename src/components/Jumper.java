package components;

import game_stuff.GamePanel;

public class Jumper {

    public static final Integer X_AXIS_PLACEMENT = 20;
    private final Integer SELF_HEIGHT = 30;
    private final Integer SELF_WIDTH = 30;
    private final Integer INITIAL_JUMPING_SPEED = 8;
    private final Double INITIAL_GRAVITY_ACCELERATION = -0.12;
    private final Integer INITIAL_HEIGHT_RELATIVE_TO_GROUND = GamePanel.GROUND_HEIGHT;

    private Integer selfHeight = SELF_HEIGHT;
    private Integer currentHeightRelativeToGround;
    private Integer timeUnit = 0;
    private Boolean isInTheAir;
    private Boolean isSquashed;
    private Integer jumpingSpeed = INITIAL_JUMPING_SPEED;
    public Double gravityAcceleration = INITIAL_GRAVITY_ACCELERATION;

    public Jumper() {
        this.currentHeightRelativeToGround = INITIAL_HEIGHT_RELATIVE_TO_GROUND;
        this.isInTheAir = false;
        this.isSquashed = false;
    }

    public void jump() {
        if (this.currentHeightRelativeToGround > INITIAL_HEIGHT_RELATIVE_TO_GROUND) {
            land();
        }

        else {
            this.currentHeightRelativeToGround -= (int)(jumpingSpeed + timeUnit * INITIAL_GRAVITY_ACCELERATION);
            timeUnit++;
        }
    }

    public Boolean isInTheAir(){
        return this.isInTheAir;
    }

    public void setJumping(){
        this.isInTheAir = true;
    }

    public Integer getCurrentHeightRelativeToGround(){
        return this.currentHeightRelativeToGround;
    }

    public Integer getBallHeight(){
        return this.selfHeight;
    }

    public Integer getBallWidth(){
        return SELF_WIDTH;
    }

    public void fall(){
        if(isInTheAir){
            this.gravityAcceleration = 0.0;
            this.jumpingSpeed = -8;
        }
    }

    public void land(){
        this.currentHeightRelativeToGround = INITIAL_HEIGHT_RELATIVE_TO_GROUND;
        this.selfHeight = SELF_HEIGHT;
        this.isInTheAir = false;
        this.jumpingSpeed = INITIAL_JUMPING_SPEED;
        this.gravityAcceleration = INITIAL_GRAVITY_ACCELERATION;
        this.timeUnit = 0;
    }

    public void squash(){
        this.selfHeight = SELF_HEIGHT/2;
        this.jumpingSpeed = (int)(INITIAL_JUMPING_SPEED*1.5);
        this.isSquashed = true;
    }

    public void unsquash(){
        this.selfHeight = SELF_HEIGHT;
        this.isInTheAir = true;
        this.isSquashed = false;
        jump();
    }

    public Boolean isSquashed(){
        return this.isSquashed;
    }
}
