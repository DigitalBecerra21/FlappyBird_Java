import java.awt.*;

public class Pipe {
    int x;
    int gapY;
    int gapHeight;
    int width;
    Color color;

    public Pipe(int x, int gapY, int gapHeight, int width, Color color) {
        this.x = x;
        this.gapY = gapY;
        this.gapHeight = gapHeight;
        this.width = width;
        this.color = color;
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(x, 0, width, gapY);
        g.fillRect(x, gapY + gapHeight, width, FlappyBird_Panel.HEIGHT - (gapY + gapHeight));
    }

    public void move() {
        x -= 4; // Move the pipe to the left
    }
}
