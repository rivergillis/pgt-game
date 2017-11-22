// EnemySpawner takes care of spawning enemies

import java.util.ArrayList;
import java.util.Random;

public class EnemySpawner {

    // New enemies spawned every ENEMY_FRAME
    private static final int ENEMY_FRAME = 60 * 3;

    // Two enemies spawned every DOUBLE_ENEMY_FRAME
    private static final int DOUBLE_ENEMY_FRAME = 60 * 7;

    private ArrayList<Enemy> enemies;
    private Random rng;

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

    public void update(int width, long frameNum) {
        if (frameNum % DOUBLE_ENEMY_FRAME == 0) {
            spawnEnemy(width);
            spawnEnemy(width);
        } else if (frameNum % ENEMY_FRAME == 0) {
            spawnEnemy(width);
        }
    }
}
