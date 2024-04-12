import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class FlappyBird_Panel extends JPanel {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    private List<Pipe> pipes;
    private Timer timer;
    private Bird bird; // Agrega el objeto Bird
    
 // Start pipe generator threads
    Thread firstPipe = new Thread(new PipeGenerator(this, this.WIDTH,"1"));
    Thread secondPipe = new Thread(new PipeGenerator(this, this.WIDTH + 200,"2"));
    Thread thirdPipe = new Thread(new PipeGenerator(this, this.WIDTH + 400,"3"));
    Thread fourthPipe = new Thread(new PipeGenerator(this, this.WIDTH + 600,"4"));

    public FlappyBird_Panel() 
    {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.CYAN);
        pipes = new ArrayList<>();
        
     // Inicializa el pájaro
        bird = new Bird(100, HEIGHT / 2); // Posición inicial del pájaro

        // Start timer for pipe movement
        timer = new Timer(20, new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                movePipes();
                bird.move(); // Mueve el pájaro en cada iteración del timer
            }
        });
        timer.start();

        
        
        startPipeGenerators();
        
     // Agrega un MouseListener para manejar los clics y hacer que el pájaro salte
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                bird.jump(); // Hace que el pájaro salte cuando se hace clic
            }
        });
    }

    private void startPipeGenerators() 
    {
        firstPipe.start();
        secondPipe.start();
        thirdPipe.start();
        fourthPipe.start();
    }
    
    private void stopPipeGenerators()
    {
    	firstPipe.interrupt();
    	secondPipe.interrupt();
    	thirdPipe.interrupt();
    	fourthPipe.interrupt();
    }

    public void addPipe(Pipe pipe) {
        pipes.add(pipe);
    }

    public void movePipes() 
    {
    	for (Pipe pipe : pipes) 
    	{
            pipe.move();
        }
        if (checkCollision()) 
        {
            gameOver();
        }
        repaint();
    }
    
    private boolean checkCollision() 
    {
        Rectangle birdBounds = bird.getBounds();
        for (Pipe pipe : pipes) {
            Rectangle pipeBounds = new Rectangle(pipe.x, 0, pipe.width, pipe.gapY);
            Rectangle lowerPipeBounds = new Rectangle(pipe.x, pipe.gapY + pipe.gapHeight, pipe.width, HEIGHT - (pipe.gapY + pipe.gapHeight));
            if (birdBounds.intersects(pipeBounds) || birdBounds.intersects(lowerPipeBounds)) {
                return true; // Hay colisión
            }
        }
        return false; // No hay colisión
    }
    
    private void gameOver() {
        timer.stop(); // Detener el temporizador
        int choice = JOptionPane.showConfirmDialog(this, "Game Over! ¿Quieres volver a intentarlo?", "Game Over", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            restartGame(); // Reiniciar el juego si el jugador elige volver a intentarlo
        } else {
            System.exit(0); // Salir del juego si el jugador elige no volver a intentarlo
        }
    }

    private void restartGame() {
    	stopPipeGenerators();
        pipes.clear(); // Limpiar todas las tuberías
        bird = new Bird(100, HEIGHT / 2); // Reiniciar el pájaro
        timer.start(); // Reiniciar el temporizador
        startPipeGenerators(); // Reiniciar la generación de tuberías
    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw pipes
        for (Pipe pipe : pipes) {
            pipe.draw(g);
        }
        
     // Dibujar el pájaro
        bird.draw(g);
    }
}
