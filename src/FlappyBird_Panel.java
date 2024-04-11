import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class FlappyBird_Panel extends JPanel {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    private List<Pipe> pipes;
    private Timer timer;

    public FlappyBird_Panel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.CYAN);
        pipes = new ArrayList<>();

        // Start timer for pipe movement
        timer = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                movePipes();
            }
        });
        timer.start();

        // Start pipe generator threads
        startPipeGenerators();
    }

    private void startPipeGenerators() {
        Thread firstPipe = new Thread(new PipeGenerator(this, this.WIDTH));
        Thread secondPipe = new Thread(new PipeGenerator(this, this.WIDTH + 200));
        Thread thirdPipe = new Thread(new PipeGenerator(this, this.WIDTH + 400));
        Thread fourthPipe = new Thread(new PipeGenerator(this, this.WIDTH + 600));

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
    }
}
