import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class FlappyBird_Panel extends JPanel 
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    private List<Pipe> pipes;
    private Timer timer;
    private Bird bird; // Agrega el objeto Bird
    private ScoreCounter scoreCounter = new ScoreCounter(WIDTH/2, 60); // Posición del contador;
    
 // Start pipe generator threads
    Thread firstPipe;
    Thread secondPipe;
    Thread thirdPipe;
    Thread fourthPipe;
    
    private BufferedImage backgroundImage;

    public FlappyBird_Panel() 
    {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        try {
            backgroundImage = ImageIO.read(new File("src/textures/bg.jpeg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        
        //Iniciar los 4 hilos para generar tuberías
        startPipeGenerators();
        //Escuchar "Enter" y "Click derecho"
        keyAndMouseListeners();
        
    }

    private void startPipeGenerators() 
    {
        //Inicialixar nuevos hilos
    	 firstPipe = new Thread(new PipeGenerator(this, WIDTH,"1"));
         secondPipe = new Thread(new PipeGenerator(this, WIDTH + 200,"2"));
         thirdPipe = new Thread(new PipeGenerator(this, WIDTH + 400,"3"));
         fourthPipe = new Thread(new PipeGenerator(this, WIDTH + 600,"4"));
    	//Correr los nuevos hilos
        firstPipe.start();
        secondPipe.start();
        thirdPipe.start();
        fourthPipe.start();
        
    }
    
    private void stopPipeGenerators()
    {
    	// Detener todos los hilos generadores de tuberías SOLO si NO están vacíos
        if (firstPipe != null) {
            firstPipe.interrupt();
        }
        if (secondPipe != null) {
            secondPipe.interrupt();
        }
        if (thirdPipe != null) {
            thirdPipe.interrupt();
        }
        if (fourthPipe != null) {
            fourthPipe.interrupt();
        }
    	
    }

    public void addPipe(Pipe pipe) {
        pipes.add(pipe);
    }

    public void movePipes() 
    {
        //Función iterada constantemente por le Timer
    	for (Pipe pipe : pipes) 
    	{
            pipe.move();
        }
        if (checkCollision()) 
        {
            gameOver();
        }
        repaint();
        
        // Verifica si el pájaro pasa por una tubería y actualiza el puntaje
        checkScore();
    }
    
    private boolean checkCollision() 
    {
        Rectangle birdBounds = bird.getBounds(); //Collider del pájaro
        for (Pipe pipe : pipes) {
            //Collider de la tubería superior
            Rectangle pipeBounds = new Rectangle(pipe.x, 0, pipe.width, pipe.gapY);
            //Collider de la tubería inferior
            Rectangle lowerPipeBounds = new Rectangle(pipe.x, pipe.gapY + pipe.gapHeight, pipe.width, HEIGHT - (pipe.gapY + pipe.gapHeight));
            if (birdBounds.intersects(pipeBounds) || birdBounds.intersects(lowerPipeBounds)) {
                return true; // Hay colisión
            }
        }
        return false; // No hay colisión
    }
    
    private void checkScore() 
    {
        for (Pipe pipe : pipes) {
            //Si el pájaro rebasa la pipe y esta no ha sido contada aún
            if (pipe.x + pipe.width < bird.getX() && !pipe.isCounted()) {
                //Entonces incrementa el score y se cuenta la pipe
                scoreCounter.incrementScore();
                pipe.setCounted(true);
            }
        }
    }

    private void gameOver() 
    {
        timer.stop(); // Detener el temporizador
        int choice = JOptionPane.showConfirmDialog(this, "Game Over! ¿Quieres volver a intentarlo?", "Game Over", JOptionPane.YES_NO_OPTION);
          if (choice == JOptionPane.YES_OPTION) 
          {
             restartGame(); // Reiniciar el juego si el jugador elige volver a intentarlo
          } 
          else 
         {
             System.exit(0); // Salir del juego si el jugador elige no volver a intentarlo
         }
          scoreCounter.resetScore();
    }

    private void restartGame() {
    	stopPipeGenerators();
        pipes.clear(); // Limpiar todas las tuberías
        bird = new Bird(100, HEIGHT / 2); // Reiniciar el pájaro
        timer.start(); // Reiniciar el temporizador
        startPipeGenerators(); // Reiniciar la generación de tuberías
    }

    public void keyAndMouseListeners()
    {
        // Agrega un MouseListener para manejar los clics y hacer que el pájaro salte
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                bird.jump(); // Hace que el pájaro salte cuando se hace clic
            }
        });

        //Keylistener para saltar con ENTER
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    bird.jump(); // Hace que el pájaro salte cuando se presiona la tecla "Enter"
                }
            }
        });
        setFocusable(true);
        requestFocusInWindow();
    }

    @Override
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);

        // Dibujar el fondo
        g.drawImage(backgroundImage, 0, 0, WIDTH, HEIGHT, null);

        // Draw pipes
        for (Pipe pipe : pipes) {
            pipe.draw(g);
        }

        //Dibujar el pájaro
        bird.draw(g);

        //Dibuja el contador de puntaje
        scoreCounter.draw(g);
    }
}
