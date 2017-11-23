import java.util.ArrayList;

public class Enemy extends GameObject {

    public static final int MAX_SPEED = 4;
    public static final int PLAYER_BULLET_DAMAGE = 20;
    public static final int MAX_HP = 180;

    // Bullets spawned every BULLET_FRAME frames
    // Game runs at about 62 frames per second
    public static final long BULLET_FRAME = 62;

    private ArrayList<Bullet> bullets;

    // health
    private int hp;

    public Enemy() {
        super("assets/64-placeholder-red.png");
        this.hp = MAX_HP;
        bullets = new ArrayList<Bullet>();
    }

    public boolean isDead() {
        return hp <= 0;
    }

    public void hit() {
        this.hp -= PLAYER_BULLET_DAMAGE;
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public void spawnBullets() {
        Bullet b1 = new Bullet(105, super.getX(), super.getY() + super.getHeight(), true);
        Bullet b3 = new Bullet(90, super.getX() + (super.getWidth() / 2), (super.getY()) + super.getHeight(), true);
        Bullet b2 = new Bullet(75, super.getX() + super.getWidth(), super.getY() + super.getHeight(), true);
        bullets.add(b1);
        bullets.add(b2);
        bullets.add(b3);
    }

    @Override
    public void updateState(int width, int height, long frameNum) {
        if (frameNum % BULLET_FRAME == 0) {
            spawnBullets();
        }
        super.updateState(width, height, frameNum);
    }
}
