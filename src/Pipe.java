import java.awt.*;

public class Pipe 
{
    int x;
    int gapY;
    int gapHeight;
    int width;
    Color color;
    String DebugString;
    boolean counted;

    public Pipe(int x, int gapY, int gapHeight, int width, Color color, String DebugString) 
    {
        this.x = x;
        this.gapY = gapY;
        this.gapHeight = gapHeight;
        this.width = width;
        this.color = color;
        this.DebugString = DebugString;
        this.counted = false;
    }

    public void draw(Graphics g) 
    {
        // g.fillRect(x, y, WIDTH, HEIGHT);
        g.setColor(color);
        g.fillRect(x, 0, width, gapY);
        g.fillRect(x, gapY + gapHeight, width, FlappyBird_Panel.HEIGHT - (gapY + gapHeight));
        
        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.PLAIN, 46));
        g.drawString(this.DebugString, x+30, gapY-30);
    }

    public void move() 
    {
        x -= 4; // Move the pipe to the left
    }
    
    public boolean isCounted() {
        return counted;
    }

    public void setCounted(boolean counted) {
        this.counted = counted;
    }
}
