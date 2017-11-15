import java.awt.*;

// GameObjects have velocities
// Measured in pixels, GameObjects move their velocities every frame
public class GameObject extends Sprite {
    private int xVel = 0;
    private int yVel = 0;

    public GameObject() {
        super("assets/64-placeholder.png");
    }

    public int getXVel() {
        return this.xVel;
    }

    public int getYVel() {
        return this.yVel;
    }

    public void setXVel(int dx) {
        this.xVel = dx;
        System.out.println("Set ship's x velocity to " + this.xVel);
    }

    public void setYVel(int dy) {
        this.yVel = dy;
    }

    @Override
    public void updateImage(Graphics g) {
        super.updateImage(g);
    }

    @Override
    public void updateState(int width, int height) {
        super.setX(super.getX() + this.getXVel());
        super.setY(super.getY() + this.getYVel());
    }
}
