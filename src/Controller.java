
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

class Controller implements MouseListener, KeyListener
{
    Model model;
    View view;

    Controller() throws IOException, Exception {
        model = new Model();
        view = new View(this);

        System.out.println("Starting background updater thread");
        Thread t = new Thread(new SpriteMover(model, view));
        t.start();
    }

    // This method is called every time the view is repainted
    public void update(Graphics g) {
        model.update(g);
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {    }
    public void mouseEntered(MouseEvent e) {    }
    public void mouseExited(MouseEvent e) {    }
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (Player.isActionKey(e)) {
            model.movePlayer(e, true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (Player.isActionKey(e)) {
            model.movePlayer(e, false);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public static void main(String[] args) throws Exception {
        new Controller();
    }
}

