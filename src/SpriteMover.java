public class SpriteMover implements Runnable {
    Model model;
    View view;
    public double perfectFrameTime = (1.0 / 60.0) * 1000;

    SpriteMover(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void run() {
        long startTime = 0L;
        double elapsedTime = 0.0;
        long timeToWait = 0L;
        //System.out.println("Perfect frame time: " + perfectFrameTime);
        while (true) {
            startTime = System.nanoTime();
            model.updateScene(view.getContentPane().getWidth(), view.getContentPane().getHeight());
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
