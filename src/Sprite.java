import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;


class Sprite
{
    private String jpgName;
    private int locationX;
    private int locationY;
    private Image image;

    public Sprite(String jpgName)
    {
        setImage(jpgName);
        locationX = 0;
        locationY = 0;
    }

    public int getX() {	return locationX; }
    public int getY() {	return locationY; }
    public void setX(int x) { locationX = x; }
    public void setY(int y) { locationY = y; }

    public void setImage(String imagePath) {
        try {
            image = ImageIO.read(new File(imagePath));
        } catch (IOException ioe) {
            System.out.println("Unable to load image file." + ioe.getMessage());
        }
    }
    public Image getImage() { return image; }

    public int getHeight() {
        return getImage().getHeight(null);
    }

    public int getWidth() {
        return getImage().getWidth(null);
    }

    // Called every time the view is repainted, really no reason to override this
    public void updateImage(Graphics g) {
        Image i = getImage();
        g.drawImage(i, getX(), getY(), getWidth(), getHeight(), null);
    }

    public boolean exitedScreen(int width, int height, int buffer) {
        if (locationX < (0 - buffer) || locationX + getWidth() > (width + buffer)) {
            return true;
        } else if (locationY < (0 - buffer) || locationY + getHeight() > (height + buffer)) {
            return true;
        } else {
            return false;
        }
    }

    public HitBox getHitBox() {
        return new HitBox(getX(), getY(), getWidth(), getHeight());
    }

    public Boolean overlaps(Sprite s) {
        return overlaps(s.getHitBox());
    }

    public Boolean overlaps(HitBox h) {
        int leftA = this.getX();
        int rightA = this.getX() + this.getWidth();
        int topA = this.getY();
        int bottomA = this.getY() + this.getHeight();

        int leftB = h.getX();
        int rightB = h.getX() + h.getWidth();
        int topB = h.getY();
        int bottomB = h.getY() + h.getHeight();

        // check if sides of a are outside of b
        if (bottomA <= topB) {
            return false;
        }

        if (topA >= bottomB) {
            return false;
        }

        if (rightA <= leftB) {
            return false;
        }

        if (leftA >= rightB) {
            return false;
        }

        return true;
    }

    // Called every frame, override this to implement the frame-by-frame logic of the object
    public void updateState(int width, int height, long frameNum) {}
}