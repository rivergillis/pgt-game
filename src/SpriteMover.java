public class SpriteMover implements Runnable {

    // SpriteMover is the background thread that handles updating the game logic
    // in a timely fashion. It is set to try to update the logic as close to
    // 60 frames per second as possible, but do to lack of precision with
    // Thread.sleep(), often accomplishes about 62 fps.

    Model model;
    View view;
    public double perfectFrameTime = (1.0 / 60.0) * 1000;

    SpriteMover(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void run() {
        // frameNumber is the total number of elapsed frames since the game started
        long frameNumber = -1L;
        long startTime = 0L;
        double elapsedTime = 0.0;
        long timeToWait = 0L;
        while (true) {
            frameNumber++;
            startTime = System.nanoTime();
            model.updateScene(view.getContentPane().getWidth(), view.getContentPane().getHeight(), frameNumber);
            view.repaint();
            elapsedTime = (System.nanoTime() - startTime) / 1000000.0;
            //System.out.println("Elapsed time for this update: " + elapsedTime);

            // sleep to make it near-60fps
            timeToWait = (long)perfectFrameTime - (long)elapsedTime;
            //System.out.println("Need to wait " + timeToWait);
            if (timeToWait > 0) {
                try {
                    Thread.sleep(timeToWait);
                } catch (InterruptedException e) {
                }
            }
        }
    }
}
