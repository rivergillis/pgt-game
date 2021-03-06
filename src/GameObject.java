import java.awt.*;

// GameObjects have velocities
// Measured in pixels, GameObjects move their velocities every frame
public class GameObject extends Sprite {
    private int xVel = 0;
    private int yVel = 0;

    // The velocity multiplier is applied to the movement on updateState
    private double velocityMultiplier = 1.0;

    public GameObject(String jpgName) {
        super(jpgName);
    }

    public int getXVel() {
        return this.xVel;
    }

    public int getYVel() {
        return this.yVel;
    }

    public void setVelocityMultiplier(double mult) {
        this.velocityMultiplier = mult;
    }

    public void setXVel(int dx) {
        this.xVel = dx;
    }

    public void setYVel(int dy) {
        this.yVel = dy;
    }

    public void moveInBounds(int roomWidth, int roomHeight) {
        if (super.getX() + super.getWidth() > roomWidth) {
            super.setX(roomWidth - super.getWidth());
        } else if (super.getX() < 0) {
            super.setX(0);
        }
        if (super.getY() + super.getHeight() > roomHeight) {
            super.setY(roomHeight - super.getHeight());
        } else if (super.getY() < 0) {
            super.setY(0);
        }
    }

    @Override
    public void updateImage(Graphics g) {
        super.updateImage(g);
    }

    @Override
    public void updateState(int width, int height, long frameNum) {
        super.setX(super.getX() + (int)(this.getXVel() * this.velocityMultiplier));
        super.setY(super.getY() + (int)(this.getYVel() * this.velocityMultiplier));
    }
}
