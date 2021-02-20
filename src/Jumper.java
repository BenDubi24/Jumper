public class Jumper {

    final Integer HEIGHT = 30;
    final Integer WIDTH = 30;
    final Integer X_AXIS_PLACEMENT = 20;
    final Integer INITIAL_JUMPING_SPEED = 8;
    final Double GRAVITY_ACCELERATION = -0.1;
    final Integer INITIAL_HEIGHT = GamePanel.GROUND_HEIGHT;

    Integer currentHeight;
    Integer timeUnit = 0;
    Boolean isJumping;

    public Jumper() {
        this.currentHeight = INITIAL_HEIGHT;
        this.isJumping = false;
    }

    public void addToCurrentHeight(double jump) {
        this.currentHeight -= (int) jump;
    }

    public void jump() {
        if (this.currentHeight > INITIAL_HEIGHT) {
            this.currentHeight = INITIAL_HEIGHT;
            isJumping = false;
            timeUnit = 0;
        } else {
            addToCurrentHeight(INITIAL_JUMPING_SPEED + timeUnit * GRAVITY_ACCELERATION);
            timeUnit++;
        }
    }
}
