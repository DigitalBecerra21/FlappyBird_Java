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

        // Start pipe generator threads
        startPipeGenerators();
        
     // Agrega un MouseListener para manejar los clics y hacer que el pájaro salte
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                bird.jump(); // Hace que el pájaro salte cuando se hace clic
            }
        });
    }

    private void startPipeGenerators() {
        Thread firstPipe = new Thread(new PipeGenerator(this, this.WIDTH,"1"));
        Thread secondPipe = new Thread(new PipeGenerator(this, this.WIDTH + 200,"2"));
        Thread thirdPipe = new Thread(new PipeGenerator(this, this.WIDTH + 400,"3"));
        Thread fourthPipe = new Thread(new PipeGenerator(this, this.WIDTH + 600,"4"));

        firstPipe.start();
        secondPipe.start();
        thirdPipe.start();
        fourthPipe.start();
    }

    public void addPipe(Pipe pipe) {
        pipes.add(pipe);
    }

    public void movePipes() {
        for (Pipe pipe : pipes) {
            pipe.move();
        }
        repaint();
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
