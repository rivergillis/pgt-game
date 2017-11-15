import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.KeyEvent;



class Model {
    private ArrayList<Sprite> sprites;
    private Player playerShip;

    Model() throws IOException {
        //Sprite sprite = new Sprite("smiley.jpg");
        synchronized (this) {
            sprites = new ArrayList<Sprite>();
            playerShip = new Player();
            sprites.add(playerShip);
            //Bank bank = new Bank();
            //sprites.add(bank);
            //lastMadeRobber = false;
        }
    }


    public void initialize() {
        /*synchronized (this) {
            sprites = new ArrayList<Sprite>();
            Bank bank = new Bank();
            sprites.add(bank);
            lastMadeRobber = false;
            RobberCar.numEscaped = 0;
            RobberCar.numCaptured = 0;
        }*/
    }

    // This method is called every time the view is repainted.
    public void update(Graphics g) {
        synchronized (this) {
            for (Sprite sprite : sprites) {
                sprite.updateImage(g);
            }
        }
    }

    public void movePlayer(KeyEvent e, int multiplier) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            playerShip.setXVel(-1 * Player.MOVESPEED_NORMAL * multiplier);
        } else if (key == KeyEvent.VK_RIGHT) {
            playerShip.setXVel(Player.MOVESPEED_NORMAL * multiplier);
        } else if (key == KeyEvent.VK_UP) {
            playerShip.setYVel(-1 * Player.MOVESPEED_NORMAL * multiplier);
        } else if (key == KeyEvent.VK_DOWN) {
            playerShip.setYVel(Player.MOVESPEED_NORMAL * multiplier);
        }
    }

    public void makeSprite(int x, int y) {
        /*synchronized (this) {
            Car sprite;
            if (lastMadeRobber) {
                sprite = new CopCar();
                lastMadeRobber = false;
                sprite.setX(x);
                sprite.setY(y);
            } else {
                sprite = new RobberCar();
                lastMadeRobber = true;
                sprite.setX(300);
                sprite.setY(300);
            }
            sprites.add(sprite);
        }*/
    }

    // This method is called every frame and should updateState() for every sprite
    public synchronized void updateScene(int width, int height) {
        for (Sprite sprite : sprites) {
            sprite.updateState(width, height);
        }
        /*synchronized (this) {
            for (Sprite sprite : sprites) {
                sprite.updateState(width, height);
            }
            for (Sprite sprite : sprites) {
                // check only CopCars
                if (!CopCar.class.isInstance(sprite)) {
                    continue;
                }
                for (Sprite other : sprites) {
                    // check only RobberCars
                    if (!RobberCar.class.isInstance(other)) {
                        continue;
                    }
                    if (sprite.overlaps(other)) {
                        RobberCar robber = (RobberCar) other;
                        robber.captured();
                    }
                }
            }
            Iterator<Sprite> i = sprites.iterator();
            while (i.hasNext()) {
                Sprite sprite = i.next();
                if (!RobberCar.class.isInstance(sprite)) {
                    continue;
                }
                RobberCar robber = (RobberCar) sprite;
                if (robber.hasEscaped()) {
                    System.out.println("I'm free!");
                    i.remove();
                }
            }
        } */
    }
}