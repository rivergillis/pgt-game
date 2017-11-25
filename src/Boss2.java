import java.util.ArrayList;

public class Boss2 extends GameObject {

    public static final int PLAYER_BULLET_DAMAGE = 20;
    public static final long BULLET_FRAME = 30;
    public static final int MAX_HP = 1500;

    private int hp = 0;

    private boolean hasBeenKilled = false;

    private ArrayList<Bullet> bullets;

    // used to alternate bullet patterns
    private boolean alternate = true;

    private long frameAfterHit = 0;

    private boolean normalImage = true;

    public Boss2(ArrayList<Bullet> bullets) {
        super("assets/bossMid.png");
        super.setX(100);
        super.setY(-300);
        this.bullets = bullets;
    }

    public boolean isAlive() {
        return this.hp > 0;
    }

    public void reset() {
        this.hp = MAX_HP;
        super.setYVel(3);
    }

    public void resetToDeath() {
        this.hp = 0;
        super.setYVel(0);
        super.setY(-300);
        super.setX(100);
        markUnkilled();
    }

    public boolean inPosition() {
        if (this.getX() == 100 && this.getY() == 0) {
            return true;
        }
        return false;
    }

    public void damage(long frameNum) {
        this.hp -= PLAYER_BULLET_DAMAGE;
        super.setImage("assets/bossMid-hit.png");
        this.frameAfterHit = frameNum + 10;
        this.normalImage = false;
    }

    public void markKilled() {
        this.hasBeenKilled = true;
    }

    public void markUnkilled() {
        this.hasBeenKilled = false;
    }

    public boolean isKilled() {
        return this.hasBeenKilled;
    }

    public void spawnBullets() {
        if (alternate) {
            Bullet b1 = new Bullet(90, super.getX(), super.getY() + super.getHeight(), true);
            Bullet b3 = new Bullet(90, super.getX() + (super.getWidth() / 2), (super.getY()) + super.getHeight(), true);
            Bullet b2 = new Bullet(90, super.getX() + super.getWidth(), super.getY() + super.getHeight(), true);
            bullets.add(b1);
            bullets.add(b2);
            bullets.add(b3);
            alternate = false;
        } else {
            Bullet b1 = new Bullet(90, super.getX() + 20, super.getY() + super.getHeight(), true);
            Bullet b3 = new Bullet(90, super.getX() + (super.getWidth() / 2) + 20, (super.getY()) + super.getHeight(), true);
            Bullet b2 = new Bullet(90, super.getX() + super.getWidth() + 20, super.getY() + super.getHeight(), true);
            bullets.add(b1);
            bullets.add(b2);
            bullets.add(b3);
            alternate = true;
        }

    }

    @Override
    public void updateState(int width, int height, long frameNum) {
        if (this.inPosition()) {
            super.setYVel(0);

            if (frameNum % BULLET_FRAME == 0) {
                spawnBullets();
            }
        }
        if (!normalImage && frameNum >= frameAfterHit) {
            super.setImage("assets/bossMid.png");
            this.normalImage = true;
        }
        super.updateState(width, height, frameNum);
    }
}
