import java.awt.*;

public class Bird {
    private int x;
    private int y;
    private int velocity;

    private static final int WIDTH = 40;
    private static final int HEIGHT = 30;

    public Bird(int x, int y) {
        this.x = x;
        this.y = y;
        this.velocity = 0;
    }

    public void jump() {
        velocity = -10; // Adjust as needed for desired jump height
    }

    public void move() {
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

    public Rectangle getBounds() {
        return new Rectangle(x, y, WIDTH, HEIGHT);
    }

    public void draw(Graphics g) {
        g.setColor(Color.YELLOW); // Change color as desired
       // g.fillRect(x, y, WIDTH, HEIGHT);
        g.fillOval(x, y, WIDTH, HEIGHT);
    }
}
