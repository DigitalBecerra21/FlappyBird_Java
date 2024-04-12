import java.awt.*;

public class ScoreCounter 
{
    private int score;
    private int x;
    private int y;
    private Font font;

    public ScoreCounter(int x, int y) 
    {
        this.x = x;
        this.y = y;
        this.score = 0;
        this.font = new Font("Arial", Font.BOLD, 52);
    }

    public void incrementScore() 
    {
        score++;
    }

    public void resetScore() 
    {
        score = 0;
    }

    public void draw(Graphics g) 
    {
        g.setFont(font);
        g.setColor(Color.BLACK);
        g.drawString(""+score, x, y);
    }
}
