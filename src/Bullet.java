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
        super("assets/16x32-placeholder-red.png");
        if (!enemy) {
            super.setImage("assets/16x32-placeholder.png");
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
        /*System.out.print("cos of " + angle + " " + Math.toRadians(Math.cos(angle)));
        System.out.println(" rounded")
        System.out.println("xvel: " + BULLET_SPEED * Math.cos(angle));
        System.out.print("Rounded: " + Math.round(BULLET_SPEED * Math.cos(angle)));
        System.out.println(" Int'd: " + (int)Math.round(BULLET_SPEED * Math.cos(angle)));*/
        super.setXVel((int)Math.round(bulletSpeed * Math.cos(radAngle)));
        super.setYVel((int)Math.round(bulletSpeed * Math.sin(radAngle)));
    }

    @Override
    public void updateState(int width, int height, long frameNum) {
        super.updateState(width, height, frameNum);
    }

    // image rotation stuff, maybe implement later
    /*
    @Override
    public void updateImage(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        AffineTransform trans = new AffineTransform();
        trans.rotate(Math.toRadians(angle));
        trans.translate(super.getX(), super.getY());
        g2d.drawImage(super.getImage(), trans, null);
    }

    public static BufferedImage toBufferedImage(Image img)
    {
        if (img instanceof BufferedImage)
        {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }*/
}
