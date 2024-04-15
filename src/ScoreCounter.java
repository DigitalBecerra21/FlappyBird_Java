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
        this.font = new Font("Arial", Font.BOLD, 62);
    }

    public void incrementScore() 
    {
        score++;
    }

    public void resetScore() 
    {
        score = 0;
    }

    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setFont(font);

        // Dibujar el contorno del texto
        g2d.setColor(Color.BLACK);
        g2d.drawString(String.valueOf(score), x - 2, y);
        g2d.drawString(String.valueOf(score), x + 2, y);
        g2d.drawString(String.valueOf(score), x, y - 2);
        g2d.drawString(String.valueOf(score), x, y + 2);

        // Dibujar el texto
        g2d.setColor(Color.WHITE);
        g2d.drawString(String.valueOf(score), x, y);
    }
}
