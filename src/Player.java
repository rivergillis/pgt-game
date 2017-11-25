import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;

public class Player extends GameObject {
    public static final int PLAYER_UP       = KeyEvent.VK_UP;
    public static final int PLAYER_DOWN     = KeyEvent.VK_DOWN;
    public static final int PLAYER_LEFT     = KeyEvent.VK_LEFT;
    public static final int PLAYER_RIGHT    = KeyEvent.VK_RIGHT;
    public static final int PLAYER_FOCUS    = KeyEvent.VK_SHIFT;
    // Bombs never got implemented :(
    public static final int PLAYER_BOMB     = KeyEvent.VK_Z;
    public static final int PLAYER_PAUSE    = KeyEvent.VK_P;

    public static final int MOVESPEED_NORMAL = 5;
    public static final int MOVESPEED_FOCUS = 3;

    // Bullets spawned every BULLET_FRAME frames
    // Game runs at about 62 frames per second
    public static final long BULLET_FRAME = 13;

    // another name: isHeld
    private HashMap<Integer, Boolean> wasLastPressed;

    // List of all Bullets we make
    private ArrayList<Bullet> bullets;


    Player(ArrayList<Bullet> playerBullets) {
        super("assets/64-player-final.png");
        wasLastPressed = new HashMap<Integer, Boolean>();
        wasLastPressed.put(PLAYER_UP, false);
        wasLastPressed.put(PLAYER_DOWN, false);
        wasLastPressed.put(PLAYER_LEFT, false);
        wasLastPressed.put(PLAYER_RIGHT, false);
        wasLastPressed.put(PLAYER_FOCUS, false);
        wasLastPressed.put(PLAYER_BOMB, false);
        wasLastPressed.put(PLAYER_PAUSE, false);
        this.bullets = playerBullets;
        resetPos();
    }

    public void resetPos() {
        super.setX(200 - 32);
        super.setY(650);
    }

    public static boolean isActionKey(KeyEvent e) {
        int k = e.getKeyCode();
        if (k == PLAYER_UP) {
            return true;
        } else if (k == PLAYER_DOWN) {
            return true;
        } else if (k == PLAYER_LEFT) {
            return true;
        } else if (k == PLAYER_RIGHT) {
            return true;
        } else if (k == PLAYER_FOCUS) {
            return true;
        } else if (k == PLAYER_BOMB) {
            return true;
        } else if (k == PLAYER_PAUSE) {
            return true;
        } else {
            return false;
        }
    }

    // Returns a hitbox for the player ship's core
    public HitBox getHeart() {
        HitBox h = new HitBox((this.getX() + 28), this.getY() + 28,8,8);
        return h;
    }

    public boolean isFocus() {
        return wasLastPressed.get(PLAYER_FOCUS);
    }

    public void setMovement(KeyEvent e, boolean isPress) {
        int k = e.getKeyCode();
        int newXVel = super.getXVel();
        int newYVel = super.getYVel();

        int changeAmount = isPress ? 1 : -1;

        // don't keep adding if the key is held
        if (wasLastPressed.get(k) && isPress) { return; }

        wasLastPressed.put(k, isPress);


        if (k == Player.PLAYER_LEFT) {
            newXVel -= changeAmount;
        } else if (k == Player.PLAYER_RIGHT) {
            newXVel += changeAmount;
        } else if (k == Player.PLAYER_UP) {
            newYVel -= changeAmount;
        } else if (k == Player.PLAYER_DOWN) {
            newYVel += changeAmount;
        } else if (k == Player.PLAYER_FOCUS) {
            if (isPress) {
                super.setImage("assets/64-player-final-heart.png");
            } else {
                super.setImage("assets/64-player-final.png");
            }
        }

        this.setXVel(newXVel);
        this.setYVel(newYVel);
    }

    public void spawnBullets() {
        //System.out.println("Spawn bullets!");
        Bullet b1 = new Bullet(360-105, super.getX(), super.getY(), false);
        Bullet b3 = new Bullet(360-90, super.getX() + (super.getWidth() / 2), super.getY() - 10, false);
        Bullet b2 = new Bullet(360-75, super.getX() + super.getWidth(), super.getY(), false);
        bullets.add(b1);
        bullets.add(b2);
        bullets.add(b3);
    }

    @Override
    public void updateState(int width, int height, long frameNum) {
        if (frameNum % BULLET_FRAME == 0) {
            spawnBullets();
        }

        if (isFocus()) {
            super.setVelocityMultiplier(MOVESPEED_FOCUS);
        } else {
            super.setVelocityMultiplier(MOVESPEED_NORMAL);
        }

        super.updateState(width, height, frameNum);
        super.moveInBounds(width, height);
    }
}
