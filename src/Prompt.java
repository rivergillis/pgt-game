import java.awt.event.KeyEvent;

public class Prompt extends Sprite {

    // Prompts are screen-sized sprites that are used to display information
    // and halt game progress until they are removed
    private boolean hasRemoved;

    public Prompt(boolean hasRemoved, boolean isDeath, boolean isVictory) {
        super("assets/instructions.png");
        if (isDeath) {
            super.setImage("assets/dead.png");
        } else if (isVictory) {
            super.setImage("assets/victory.png");
        }
        this.hasRemoved = hasRemoved;
    }

    public boolean isRemoved() {
        return this.hasRemoved;
    }

    // remove the prompt when the user presses 'bomb'
    public boolean remove(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == Player.PLAYER_BOMB) {
            this.hasRemoved = true;
            return true;
        }
        return false;
    }

    public void forceRemove() {
        this.hasRemoved = true;
    }

    public void unRemove() {
        this.hasRemoved = false;
    }
}
