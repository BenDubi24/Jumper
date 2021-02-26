package components;

import game_stuff.GamePanel;

public class Jumper {

    private final Integer X_VALUE = 20;
    private final Integer INITIAL_Y_VALUE = GamePanel.GROUND_HEIGHT;
    private final Integer DEFAULT_HEIGHT = 30;
    private final Integer WIDTH = 30;
    private final Integer INITIAL_JUMPING_SPEED = 8;
    private final Double INITIAL_GRAVITY_ACCELERATION = -0.12;

    private Integer height;
    private Integer y;
    private Integer timeSpentInTheAir;
    private Integer jumpingSpeed;
    private Double gravityAcceleration;
    private Boolean isInTheAir;
    private Boolean isSquashed;

    public Jumper() {
        this.height = DEFAULT_HEIGHT;
        this.y = INITIAL_Y_VALUE;
        this.timeSpentInTheAir = 0;
        this.jumpingSpeed = INITIAL_JUMPING_SPEED;
        this.gravityAcceleration = INITIAL_GRAVITY_ACCELERATION;
        this.isInTheAir = false;
        this.isSquashed = false;
    }

    public void keepJumping() {
        if (this.y > INITIAL_Y_VALUE) {
            land();
        }

        else {
            this.y -= (int)(jumpingSpeed + timeSpentInTheAir * INITIAL_GRAVITY_ACCELERATION);
            timeSpentInTheAir++;
        }
    }

    public Boolean isInTheAir(){
        return this.isInTheAir;
    }

    public void setIsInTheAirTrue(){
        this.isInTheAir = true;
    }

    public Integer getX(){
        return X_VALUE;
    }

    public Integer getY(){
        return this.y;
    }

    public Integer getHeight(){
        return this.height;
    }

    public Integer getWidth(){
        return WIDTH;
    }

    public void fall(){
        if(isInTheAir){
            this.gravityAcceleration = 0.0;
            this.jumpingSpeed = -8;
        }
    }

    public void land(){
        this.y = INITIAL_Y_VALUE;
        this.height = DEFAULT_HEIGHT;
        this.isInTheAir = false;
        this.jumpingSpeed = INITIAL_JUMPING_SPEED;
        this.gravityAcceleration = INITIAL_GRAVITY_ACCELERATION;
        this.timeSpentInTheAir = 0;
    }

    public void squash(){
        this.height = DEFAULT_HEIGHT/2;
        this.jumpingSpeed = (int)(INITIAL_JUMPING_SPEED*1.5);
        this.isSquashed = true;
    }

    public void unsquash(){
        this.height = DEFAULT_HEIGHT;
        this.isInTheAir = true;
        this.isSquashed = false;
    }

    public Boolean isSquashed(){
        return this.isSquashed;
    }
}
