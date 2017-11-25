// When I want to rotate this in the future, cast the graphics to a graphics2d then use
// an affinetransform

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Bullet extends GameObject {

    private static final int BULLET_SPEED = 5;
    private static final int BULLET_SPEED_SLOW = 3;

    private int angle;
    private boolean enemy;

    public Bullet(int angle, int x, int y, boolean enemy) {
        super("assets/16x32-bullet-red.png");
        if (!enemy) {
            super.setImage("assets/16x32-bullet-blue.png");
        }
        this.enemy = enemy;
        this.setAngle(angle);
        super.setX(x);
        super.setY(y);
    }

    public void setAngle(int angle) {
        this.angle = angle;
        double radAngle = Math.toRadians(angle);
        int bulletSpeed = 0;

        if (this.enemy) {
            bulletSpeed = BULLET_SPEED_SLOW;
        } else {
            bulletSpeed = BULLET_SPEED;
        }
        super.setXVel((int)Math.round(bulletSpeed * Math.cos(radAngle)));
        super.setYVel((int)Math.round(bulletSpeed * Math.sin(radAngle)));
    }

    @Override
    public void updateState(int width, int height, long frameNum) {
        super.updateState(width, height, frameNum);
    }
}
