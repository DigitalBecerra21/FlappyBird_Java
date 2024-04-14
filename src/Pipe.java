import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Pipe 
{
    int x;
    int gapY;
    int gapHeight;
    int width;
    Color color;
    String DebugString;
    boolean counted;

    private BufferedImage pipeImageTop; // Textura para la tubería superior
    private BufferedImage pipeImageBottom; // Textura para la tubería inferior
    
    public Pipe(int x, int gapY, int gapHeight, int width, Color color, String DebugString) {
        this.x = x;
        this.gapY = gapY;
        this.gapHeight = gapHeight;
        this.width = width;
        this.color = color;
        this.DebugString = DebugString;
        this.counted = false;

        // Cargar las imágenes de la tubería desde la carpeta textures
        try {
            pipeImageTop = ImageIO.read(new File("src/textures/pipe.png")); // Textura para la tubería superior
            pipeImageBottom = ImageIO.read(new File("src/textures/pipe2.png")); // Textura para la tubería inferior
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        

    public void draw(Graphics g) {
        
        g.drawImage(pipeImageTop, x, 0, width, gapY, null); // Dibuja la parte superior de la tubería con la textura de pipe.png
        g.drawImage(pipeImageBottom, x, gapY + gapHeight, width, FlappyBird_Panel.HEIGHT - (gapY + gapHeight), null); // Dibuja la parte inferior de la tubería con la textura de pipe2.png

        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.PLAIN, 46));
        g.drawString(this.DebugString, x + 30, gapY - 30);
    }

    public void move() 
    {
        x -= 4; // Mueve la tubería hacia la izquierda
    }
    
    public boolean isCounted() {
        return counted;
    }

    public void setCounted(boolean counted) {
        this.counted = counted;
    }
}
