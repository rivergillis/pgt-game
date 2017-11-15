import java.awt.event.KeyEvent;

public class Player extends GameObject {
    public static final int PLAYER_UP       = KeyEvent.VK_UP;
    public static final int PLAYER_DOWN     = KeyEvent.VK_DOWN;
    public static final int PLAYER_LEFT     = KeyEvent.VK_LEFT;
    public static final int PLAYER_RIGHT    = KeyEvent.VK_RIGHT;
    public static final int PLAYER_FOCUS    = KeyEvent.VK_SHIFT;
    public static final int PLAYER_BOMB     = KeyEvent.VK_Z;

    public static final int MOVESPEED_NORMAL = 5;
    public static final int MOVESPEED_FOCUS = 3;

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
}
