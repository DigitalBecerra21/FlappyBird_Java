import java.awt.*;

public class Pipe 
{
    int x;
    int gapY;
    int gapHeight;
    int width;
    Color color;
    String DebugString;

    public Pipe(int x, int gapY, int gapHeight, int width, Color color, String DebugString) 
    {
        this.x = x;
        this.gapY = gapY;
        this.gapHeight = gapHeight;
        this.width = width;
        this.color = color;
        this.DebugString = DebugString;
    }

    public void draw(Graphics g) 
    {
        g.setColor(color);
        g.fillRect(x, 0, width, gapY);
        g.fillRect(x, gapY + gapHeight, width, FlappyBird_Panel.HEIGHT - (gapY + gapHeight));
        
        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.PLAIN, 96));
        g.drawString(this.DebugString, x+30, gapY);
    }

    public void move() 
    {
        x -= 4; // Move the pipe to the left
    }
}
