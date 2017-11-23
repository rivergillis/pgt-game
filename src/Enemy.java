import java.util.ArrayList;

public class Enemy extends GameObject {

    public static final int MAX_SPEED = 4;
    public static final int PLAYER_BULLET_DAMAGE = 20;
    public static final int MAX_HP = 180;

    private ArrayList<Bullet> bullets;

    // health
    private int hp;

    public Enemy() {
        super("assets/64-placeholder-red.png");
        this.hp = MAX_HP;
    }

    public boolean isDead() {
        return hp <= 0;
    }

    public void hit() {
        this.hp -= PLAYER_BULLET_DAMAGE;
    }
}
