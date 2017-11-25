import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.KeyEvent;
import java.util.Iterator;


class Model {
    // Holds everything we render
    private ArrayList<Sprite> sprites;
    private ArrayList<Bullet> playerBullets;
    // Dead enemy bullets get adopted from enemies when they die
    private ArrayList<Bullet> deadEnemyBullets;
    private ArrayList<Bullet> bossBullets;

    private ArrayList<Enemy> enemies;
    private Player playerShip;

    private EnemySpawner spawner;

    private Prompt instructions;
    private Prompt deathPrompt;
    private Prompt victoryPrompt;

    private Boss1 bossLeft;
    private Boss2 bossMid;
    private Boss3 bossRight;

    // The scrolling background image
    private ScrollBg bg;

    private int points = 0;

    Model() throws IOException {
        synchronized (this) {
            sprites = new ArrayList<Sprite>();
            playerBullets = new ArrayList<Bullet>();
            enemies = new ArrayList<Enemy>();
            deadEnemyBullets = new ArrayList<Bullet>();
            bossBullets = new ArrayList<Bullet>();

            playerShip = new Player(playerBullets);
            bg = new ScrollBg();
            sprites.add(bg);
            sprites.add(playerShip);
            spawner = new EnemySpawner(enemies);

            this.instructions = new Prompt(false, false, false);
            this.deathPrompt = new Prompt(true, true, false);
            this.victoryPrompt = new Prompt(true, false, true);
            sprites.add(instructions);

            bossLeft = new Boss1(bossBullets);
            bossMid = new Boss2(bossBullets);
            bossRight = new Boss3(bossBullets);
        }
    }

    // This method is called every time the view is repainted.
    public void update(Graphics g) {
        synchronized (this) {
            for (Sprite sprite : sprites) {
                sprite.updateImage(g);
            }
            if (!instructions.isRemoved() || !deathPrompt.isRemoved() || !victoryPrompt.isRemoved()) {
                return;
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
            for (Sprite bullet : bossBullets) {
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
        } else if (!this.victoryPrompt.isRemoved()) {
            if (this.victoryPrompt.remove(e)) {
                sprites.remove(victoryPrompt);
            }
        } else if (e.getKeyCode() == Player.PLAYER_PAUSE) {
            if (this.instructions.isRemoved()) {
                this.instructions.unRemove();
                sprites.add(instructions);
            }
        }
        playerShip.setMovement(e, isPress);
    }

    public void killPlayerAndReset(boolean won) {
        // Pretty much just resets our ArrayLists and the player's position and score
        sprites.clear();
        sprites.add(bg);
        sprites.add(playerShip);

        playerBullets.clear();
        deadEnemyBullets.clear();
        bossBullets.clear();
        enemies.clear();

        if (!won) {
            System.out.println("Died with " + points + " points");
        } else {
            System.out.println("Won the game with " + (points + 5000) + " points");
        }
        this.points = 0;
        deathPrompt.unRemove();
        sprites.add(deathPrompt);

        bossLeft.resetToDeath();
        bossMid.resetToDeath();
        bossRight.resetToDeath();

        playerShip.resetPos();
    }

    // Damages enemies if they get hit by any bullets
    // Returns true if the enemy should be removed
    public boolean shouldRemoveEnemy(Enemy enemy, int height, long frameNum) {
        if (enemy.getY() > (height + 20)) {
            return true;
        }
        // check collisions with every bullet
        Iterator<Bullet> iB = playerBullets.iterator();
        while (iB.hasNext()) {
            Bullet b = iB.next();
            if (b.overlaps(enemy)) {
                enemy.hit(frameNum);
                iB.remove();
                if (enemy.isDead()) {
                    points += Enemy.ENEMY_POINTS;
                    return true;
                }
            }
        }
        return false;
    }

    // Resets everything as if the player had died, but with a different prompt.
    public void winGame() {
        killPlayerAndReset(true);
        deathPrompt.forceRemove();
        sprites.remove(deathPrompt);
        victoryPrompt.unRemove();
        sprites.add(victoryPrompt);
    }

    // Checks boss collisions, wins the game if needed
    public void damageBoss(long frameNum) {
        // check collisions with every bullet
        Iterator<Bullet> iB = playerBullets.iterator();
        while (iB.hasNext()) {
            Bullet b = iB.next();
            if (b.overlaps(bossLeft)) {
                bossLeft.damage(frameNum);
                iB.remove();
                if (!bossLeft.isAlive()) {
                    bossLeft.markKilled();
                }
            } else if (b.overlaps(bossMid)) {
                bossMid.damage(frameNum);
                iB.remove();
                if (!bossMid.isAlive()) {
                    bossMid.markKilled();
                }
            } else if (b.overlaps(bossRight)) {
                bossRight.damage(frameNum);
                iB.remove();
                if (!bossRight.isAlive()) {
                    bossRight.markKilled();
                }
            }
        }

        if (bossLeft.isKilled() && bossMid.isKilled() && bossRight.isKilled()) {
            winGame();
        }
    }

    // Pulls out bullets from dead enemies
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
        // Active prompts will pause the game
        if (!this.instructions.isRemoved()) { return; }
        if (!this.deathPrompt.isRemoved()) { return; }
        if (!this.victoryPrompt.isRemoved()) { return; }
        for (Sprite sprite : sprites) {
            sprite.updateState(width, height, frameNum);
        }
        updateBullets(playerBullets, false, width, height, frameNum);

        Iterator<Enemy> iE = enemies.iterator();
        while (iE.hasNext()) {
            Enemy enemy = iE.next();
            enemy.updateState(width, height, frameNum);
            if (updateBullets(enemy.getBullets(), true, width, height, frameNum)) {
                killPlayerAndReset(false);
                return;
            }

            if (shouldRemoveEnemy(enemy, height, frameNum)) {
                adoptBulletsFromDeadEnemy(enemy);
                iE.remove();
            }
        }
        if (updateBullets(deadEnemyBullets, true, width, height, frameNum)) {
            killPlayerAndReset(false);
            return;
        }
        if (updateBullets(bossBullets, true, width, height, frameNum)) {
            killPlayerAndReset(false);
            return;
        }
        if (spawner.update(width, frameNum, this.points)) {
            spawnBoss();
        }

        damageBoss(frameNum);

        if (bossLeft.isAlive()) {
            bossLeft.updateState(width, height, frameNum);
        }
        if (bossMid.isAlive()) {
            bossMid.updateState(width, height, frameNum);
        }
        if (bossRight.isAlive()) {
            bossRight.updateState(width, height, frameNum);
        }
    }

    public void spawnBoss() {
        bossLeft.reset();
        bossMid.reset();
        bossRight.reset();
        for (Enemy e : enemies) {
            adoptBulletsFromDeadEnemy(e);
        }
        enemies.clear();
    }
}