import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Bird {
    private int x;
    private int y;
    private int velocity;

    private static final int WIDTH = 40;
    private static final int HEIGHT = 30;
    
    private BufferedImage birdImage;

    public Bird(int x, int y) 
    {
        this.x = x;
        this.y = y;
        this.velocity = 0;
    
     // Cargar la imagen del pájaro desde la carpeta textures
        try {
            birdImage = ImageIO.read(new File("src/textures/bird.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    public void jump() 
    {
        velocity = -10; // Adjust as needed for desired jump height
    }

    public void move() 
    {
        y += velocity;
        
     // Limitar la posición del pájaro dentro de los límites de la pantalla
        if (y < 0) { // Borde superior
            y = 0;
            velocity = 0; // Detiene el movimiento hacia arriba
        } else if (y > FlappyBird_Panel.HEIGHT - HEIGHT) { // Borde inferior
            y = FlappyBird_Panel.HEIGHT - HEIGHT;
            velocity = 0; // Detiene el movimiento hacia abajo
        } else {
            // Si el pájaro no ha alcanzado los límites de la pantalla, aplica gravedad
            velocity += 1; // Gravedad
        }
    }

    public Rectangle getBounds() 
    {
        return new Rectangle(x, y, WIDTH, HEIGHT);
    }
    
    public int getX() 
    {
        return x;
    }

    public void draw(Graphics g) 
    {
    	 g.drawImage(birdImage, x, y, WIDTH, HEIGHT, null);
    }
}
