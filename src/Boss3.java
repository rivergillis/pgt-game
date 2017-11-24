public class Boss3 extends GameObject {

    public static final int MAX_HP = 2000;

    private int hp = 0;

    public Boss3() {
        super("assets/bossRight.png");
        super.setX(300);
        super.setY(-300);
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
        super.setX(300);
    }

    public boolean inPosition() {
        if (this.getX() == 300 && this.getY() == 0) {
            return true;
        }
        return false;
    }

    @Override
    public void updateState(int width, int height, long frameNum) {
        if (this.inPosition()) {
            super.setYVel(0);
        }
        super.updateState(width, height, frameNum);
    }
}
