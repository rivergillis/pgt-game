public class Boss2 extends Sprite {

    public static final int MAX_HP = 5000;

    private int hp;

    public Boss2() {
        super("assets/bossMid.png");
        this.hp = MAX_HP;
        super.setX(100);
    }

    public boolean isAlive() {
        return this.hp > 0;
    }
}
