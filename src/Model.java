import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.KeyEvent;
import java.util.Iterator;


class Model {
    private ArrayList<Sprite> sprites;
    private ArrayList<Bullet> playerBullets;
    private ArrayList<Bullet> deadEnemyBullets;

    private ArrayList<Enemy> enemies;
    private Player playerShip;

    private EnemySpawner spawner;

    private Prompt instructions;
    private Prompt deathPrompt;

    Model() throws IOException {
        synchronized (this) {
            sprites = new ArrayList<Sprite>();
            playerBullets = new ArrayList<Bullet>();
            enemies = new ArrayList<Enemy>();
            deadEnemyBullets = new ArrayList<Bullet>();
            playerShip = new Player(playerBullets);
            sprites.add(playerShip);
            spawner = new EnemySpawner(enemies);
            this.instructions = new Prompt(false, false);
            this.deathPrompt = new Prompt(true, true);
            sprites.add(instructions);
        }
    }


    public void initialize() {
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
                for (Sprite bullet : e.getBullets()) {
                    bullet.updateImage(g);
                }
            }
            for (Sprite bullet : deadEnemyBullets) {
                bullet.updateImage(g);
            }
        }
    }

    public void movePlayer(KeyEvent e, boolean isPress) {
        if (!this.instructions.isRemoved()) {
            if (this.instructions.remove(e)) {
                sprites.remove(instructions);
            }
        } else {
            playerShip.setMovement(e, isPress);
        }
    }

    public void killPlayerAndReset() {
        sprites.clear();
        sprites.add(playerShip);
        playerBullets.clear();
        deadEnemyBullets.clear();
        enemies.clear();
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

    private void adoptBulletsFromDeadEnemy(Enemy enemy) {
        ArrayList<Bullet> enemyBullets = enemy.getBullets();
        deadEnemyBullets.addAll(enemyBullets);
    }

    // if enemyBullets, updateBullets will return true if a bullet hit the player
    public boolean updateBullets(ArrayList<Bullet> bullets, boolean enemyBullets, int width, int height, long frameNum) {
        Iterator<Bullet> i = bullets.iterator();
        while (i.hasNext()) {
            Bullet b = i.next();
            b.updateState(width, height, frameNum);
            if (b.exitedScreen(width, height, 20)) {
                i.remove();
            } else if (enemyBullets) {
                if (b.overlaps(playerShip.getHeart())) {
                    return true;
                }
            }
        }
        return false;
    }

    // This method is called every frame and should updateState() for every sprite
    public synchronized void updateScene(int width, int height, long frameNum) {
        if (!this.instructions.isRemoved()) { return; }
        for (Sprite sprite : sprites) {
            sprite.updateState(width, height, frameNum);
        }
        updateBullets(playerBullets, false, width, height, frameNum);

        Iterator<Enemy> iE = enemies.iterator();
        while (iE.hasNext()) {
            Enemy enemy = iE.next();
            enemy.updateState(width, height, frameNum);
            if (updateBullets(enemy.getBullets(), true, width, height, frameNum)) {
                killPlayerAndReset();
                return;
            }

            if (shouldRemoveEnemy(enemy, height)) {
                adoptBulletsFromDeadEnemy(enemy);
                iE.remove();
            }
        }
        if (updateBullets(deadEnemyBullets, true, width, height, frameNum)) {
            killPlayerAndReset();
            return;
        }
        spawner.update(width, frameNum);
    }
}