// EnemySpawner takes care of spawning enemies

import java.util.ArrayList;
import java.util.Random;

public class EnemySpawner {

    // New enemies spawned every ENEMY_FRAME
    private static final int ENEMY_FRAME = 60 * 3;

    // Two enemies spawned every DOUBLE_ENEMY_FRAME
    private static final int DOUBLE_ENEMY_FRAME = 60 * 7;

    // The score at which point the boss appears
    private static final int BOSS_SCORE = 1000;

    private ArrayList<Enemy> enemies;
    private Random rng;

    private boolean spawnedBoss = false;

    public EnemySpawner(ArrayList<Enemy> enemies) {
        this.enemies = enemies;
        this.rng = new Random();
    }

    public void spawnEnemy(int width) {
        int xLoc = rng.nextInt(width);
        int yLoc = -100;
        //System.out.println("Spawning new enemies at x: " + xLoc + " y: " + yLoc);
        Enemy e = new Enemy();
        e.setX(xLoc);
        e.setY(yLoc);
        e.setYVel(rng.nextInt(Enemy.MAX_SPEED - 1) + 1);
        enemies.add(e);
    }

    // returns true if the boss needs to spawn
    public boolean update(int width, long frameNum, int score) {
        if (score >= BOSS_SCORE) {
            if (!spawnedBoss) {
                spawnedBoss = true;
                return true;
            }
            return false;
        }
        else if (frameNum % DOUBLE_ENEMY_FRAME == 0) {
            spawnEnemy(width);
            spawnEnemy(width);
        } else if (frameNum % ENEMY_FRAME == 0) {
            spawnEnemy(width);
        }

        // to handle death during boss fight
        spawnedBoss = false;

        return false;
    }
}
