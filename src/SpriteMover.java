public class SpriteMover implements Runnable {
    Model model;
    View view;

    SpriteMover(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void run() {
        while (true) {
            model.updateScene(view.getWidth(), view.getHeight());
            view.repaint();
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {}
        }
    }
}
