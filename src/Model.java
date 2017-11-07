import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;


class Model {
    private ArrayList<Sprite> sprites;

    Model() throws IOException {
        //Sprite sprite = new Sprite("smiley.jpg");
        synchronized (this) {
            sprites = new ArrayList<Sprite>();
            Ship ship = new Ship();
            sprites.add(ship);
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

    public void update(Graphics g) {
        synchronized (this) {
            for (Sprite sprite : sprites) {
                sprite.updateImage(g);
            }
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

    public void updateScene(int width, int height) {
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