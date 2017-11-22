// When I want to rotate this in the future, cast the graphics to a graphics2d then use
// an affinetransform

public class Bullet extends GameObject {

    private int angle;

    public Bullet(int angle) {
        super("assets/16x32-placeholder.png");
        this.angle = angle;
    }
}
