
import javax.swing.*;
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
        if (SwingUtilities.isLeftMouseButton(e)) {
            // Gets here is left mouse button was clicked
        } else if (SwingUtilities.isRightMouseButton(e))  {
            // Gets here if right mouse button was clicked
        }
    }

    public void mouseReleased(MouseEvent e) {    }
    public void mouseEntered(MouseEvent e) {    }
    public void mouseExited(MouseEvent e) {    }
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            // left click
            model.makeSprite(e.getX(), e.getY());
        } else {
            // right click
            model.updateScene(view.getWidth(), view.getHeight());
        }
        view.repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (Player.isActionKey(e)) {
            model.movePlayer(e, true);
        }

        int key = e.getKeyCode();
        if (key == KeyEvent.VK_H) {
            System.out.println("hello world");
        } else if (key == KeyEvent.VK_N) {
            //System.out.println("Robbers Captured: " + RobberCar.numCaptured + "\tRobbers Escaped: " + RobberCar.numEscaped);
        } else if (key == KeyEvent.VK_R) {
            model.initialize();
            view.repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (Player.isActionKey(e)) {
            model.movePlayer(e, false);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public static void main(String[] args) throws Exception {
        //  Use the following line to determine which directory your program
        //  is being executed from, since that is where the image files will
        //  need to be.
        //System.out.println("cwd=" + System.getProperty("user.dir"));
        new Controller();
    }
}

