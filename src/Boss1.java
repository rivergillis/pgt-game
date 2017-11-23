public class Boss1 extends Sprite {

    public static final int MAX_HP = 2000;

    private int hp;

    public Boss1() {
        super("assets/bossLeft.png");
        this.hp = MAX_HP;
    }

    public boolean isAlive() {
        return this.hp > 0;
    }
}
