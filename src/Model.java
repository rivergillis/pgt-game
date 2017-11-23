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

    private Boss1 bossLeft;
    private Boss2 bossMid;
    private Boss3 bossRight;

    private int points = 0;

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

            bossLeft = new Boss1();
            bossMid = new Boss2();
            bossRight = new Boss3();
        }
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

            if (bossLeft.isAlive()) {
                bossLeft.updateImage(g);
            }
            if (bossMid.isAlive()) {
                bossMid.updateImage(g);
            }
            if (bossRight.isAlive()) {
                bossRight.updateImage(g);
            }
        }
    }

    public void movePlayer(KeyEvent e, boolean isPress) {
        if (!this.instructions.isRemoved()) {
            if (this.instructions.remove(e)) {
                sprites.remove(instructions);
            }
        } else if (!this.deathPrompt.isRemoved()) {
            if (this.deathPrompt.remove(e)) {
                sprites.remove(deathPrompt);
            }
        }
        playerShip.setMovement(e, isPress);
    }

    public void killPlayerAndReset() {
        sprites.clear();
        sprites.add(playerShip);
        playerBullets.clear();
        deadEnemyBullets.clear();
        enemies.clear();
        System.out.println("Died with " + points + " points");
        this.points = 0;
        deathPrompt.unRemove();
        sprites.add(deathPrompt);
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
                    points += Enemy.ENEMY_POINTS;
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
        if (!this.deathPrompt.isRemoved()) { return; }
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
        spawner.update(width, frameNum, this.points);
    }
}