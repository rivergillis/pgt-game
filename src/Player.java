import java.awt.event.KeyEvent;
import java.util.HashMap;

public class Player extends GameObject {
    public static final int PLAYER_UP       = KeyEvent.VK_UP;
    public static final int PLAYER_DOWN     = KeyEvent.VK_DOWN;
    public static final int PLAYER_LEFT     = KeyEvent.VK_LEFT;
    public static final int PLAYER_RIGHT    = KeyEvent.VK_RIGHT;
    public static final int PLAYER_FOCUS    = KeyEvent.VK_SHIFT;
    public static final int PLAYER_BOMB     = KeyEvent.VK_Z;

    public static final int MOVESPEED_NORMAL = 5;
    public static final int MOVESPEED_FOCUS = 3;

    // another name: isHeld
    // idea: make this ternary (0, focused, normal)
    private HashMap<Integer, Boolean> wasLastPressed;


    Player() {
        super();
        wasLastPressed = new HashMap<Integer, Boolean>();
        wasLastPressed.put(PLAYER_UP, false);
        wasLastPressed.put(PLAYER_DOWN, false);
        wasLastPressed.put(PLAYER_LEFT, false);
        wasLastPressed.put(PLAYER_RIGHT, false);
        wasLastPressed.put(PLAYER_FOCUS, false);
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
        } else {
            return false;
        }
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
                super.setImage("assets/64-placeholder-heart.png");
            } else {
                super.setImage("assets/64-placeholder.png");
            }
        }

        this.setXVel(newXVel);
        this.setYVel(newYVel);
    }

    @Override
    public void updateState(int width, int height) {
        if (isFocus()) {
            super.setVelocityMultiplier(MOVESPEED_FOCUS);
        } else {
            super.setVelocityMultiplier(MOVESPEED_NORMAL);
        }

        super.updateState(width, height);
        super.moveInBounds(width, height);
    }
}
