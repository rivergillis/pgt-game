public class Boss3 extends Sprite {

    public static final int MAX_HP = 2000;

    private int hp;

    public Boss3() {
        super("assets/bossRight.png");
        this.hp = MAX_HP;
        super.setX(300);
    }

    public boolean isAlive() {
        return this.hp > 0;
    }
}
