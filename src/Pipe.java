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
    Color color = Color.GREEN;
    String DebugString;
    boolean counted;

    private BufferedImage pipeImageTop; // Textura para la tubería superior
    private BufferedImage pipeImageBottom; // Textura para la tubería inferior
    
    public Pipe(int x, int gapY, int gapHeight, int width, String DebugString) {
        this.x = x;
        this.gapY = gapY;
        this.gapHeight = gapHeight;
        this.width = width;
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

        int fixedHeigh = 400;

        // Green rectangles (FOR DEBUGGING)
        /*
        g.setColor(color);
        g.fillRect(x, 0, width, gapY);
        g.fillRect(x, gapY + gapHeight, width, FlappyBird_Panel.HEIGHT - (gapY + gapHeight));
        */

        // Dibujar las imágenes de las tuberías
        g.drawImage(pipeImageTop, x, gapY-fixedHeigh, width, fixedHeigh, null); // Dibujar la tubería superior
        g.drawImage(pipeImageBottom, x, gapY + gapHeight, width, fixedHeigh, null); // Dibujar la tubería inferior
        
        //Número de hilo
        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.PLAIN, 46));
        g.drawString(this.DebugString, x + 30, gapY - 40);
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
