import java.awt.*;
import java.awt.image.BufferedImage;

public class ScrollBg extends Sprite {
    private BufferedImage bImage;

    private int scrollX = 0;

    public ScrollBg() {
        super("assets/bg.png");
        this.bImage = toBufferedImage(this.getImage());
    }

    public static BufferedImage toBufferedImage(Image img)
    {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bImage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bImage;
    }

    @Override
    public void updateImage(Graphics g) {
        BufferedImage toRender = bImage.getSubimage(0,scrollX, 400, 800);
        g.drawImage(toRender, getX(), getY(), 400, 800, null);
    }

    @Override
    public void updateState(int width, int height, long frameNum) {
        this.scrollX += 1;
        if (this.scrollX >= 800) {
            this.scrollX = 0;
        }
    }
}
