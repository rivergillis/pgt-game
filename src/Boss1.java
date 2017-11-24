public class Boss1 extends GameObject {

    public static final int PLAYER_BULLET_DAMAGE = 20;

    public static final int MAX_HP = 100;

    private int hp = 0;

    private boolean hasBeenKilled = false;

    public Boss1() {
        super("assets/bossLeft.png");
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
        super.setX(0);
        markUnkilled();
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

    public boolean inPosition() {
        if (this.getX() == 0 && this.getY() == 0) {
            return true;
        }
        return false;
    }

    public void damage() {
        this.hp -= PLAYER_BULLET_DAMAGE;
    }

    @Override
    public void updateState(int width, int height, long frameNum) {
        if (this.inPosition()) {
            super.setYVel(0);
        }
        super.updateState(width, height, frameNum);
    }
}
