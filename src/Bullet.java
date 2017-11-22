// When I want to rotate this in the future, cast the graphics to a graphics2d then use
// an affinetransform

public class Bullet extends GameObject {

    private int angle;

    public Bullet(int angle, int x, int y) {
        super("assets/16x32-placeholder.png");
        this.setAngle(angle);
        super.setX(x);
        super.setY(y);
    }

    public void setAngle(int angle) {
        this.angle = angle;
        super.setXVel(2);
        super.setYVel(2);
    }

    @Override
    public void updateState(int width, int height, long frameNum) {
        System.out.println("Bullet update state");
        super.updateState(width, height, frameNum);
    }
}
