import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.KeyEvent;
import java.util.Iterator;


class Model {
    private ArrayList<Sprite> sprites;
    private ArrayList<Bullet> playerBullets;

    private ArrayList<Enemy> enemies;
    private ArrayList<Bullet> enemyBullets;
    private Player playerShip;

    private EnemySpawner spawner;

    Model() throws IOException {
        //Sprite sprite = new Sprite("smiley.jpg");
        synchronized (this) {
            sprites = new ArrayList<Sprite>();
            playerBullets = new ArrayList<Bullet>();
            enemies = new ArrayList<Enemy>();
            playerShip = new Player(playerBullets);
            sprites.add(playerShip);
            spawner = new EnemySpawner(enemies);
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
            for (Sprite bullet : playerBullets) {
                bullet.updateImage(g);
            }
            for (Enemy e: enemies) {
                e.updateImage(g);
            }
        }
    }

    public void movePlayer(KeyEvent e, boolean isPress) {
        playerShip.setMovement(e, isPress);
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

    public boolean shouldRemoveEnemy(Enemy enemy, int height) {
        if (enemy.getY() > (height + 20)) {
            return true;
        }
        // check collisions with every bullet
        Iterator<Bullet> iB = playerBullets.iterator();
        while (iB.hasNext()) {
            Bullet b = iB.next();
            if (b.overlaps(enemy)) {
                enemy.hit();
                iB.remove();
                if (enemy.isDead()) {
                    return true;
                }
            }
        }
        return false;
    }

    // This method is called every frame and should updateState() for every sprite
    public synchronized void updateScene(int width, int height, long frameNum) {
        for (Sprite sprite : sprites) {
            sprite.updateState(width, height, frameNum);
        }
        Iterator<Bullet> i = playerBullets.iterator();
        while (i.hasNext()) {
            Bullet b = i.next();
            b.updateState(width, height, frameNum);
            if (b.exitedScreen(width, height, 20)) {
                i.remove();
            }
        }
        Iterator<Enemy> iE = enemies.iterator();
        while (iE.hasNext()) {
            Enemy enemy = iE.next();
            enemy.updateState(width, height, frameNum);
            if (shouldRemoveEnemy(enemy, height)) {
                iE.remove();
            }
        }
        spawner.update(width, frameNum);
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