import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PipeManager {
    private static final int PIPE_GAP = 200;
    private static final int PIPE_WIDTH = 50;
    private static final int PIPE_SPEED = 2;

    private List<Pipe> pipes;

    public PipeManager() {
        pipes = new ArrayList<>();
        initializePipes();
    }

    private void initializePipes() {
        int x = FlappyBird_Panel.WIDTH;
        while (x < FlappyBird_Panel.WIDTH * 2) {
            int gapY = (int) (Math.random() * (FlappyBird_Panel.HEIGHT - PIPE_GAP));
            Pipe pipe = new Pipe(x, gapY, PIPE_GAP);
            pipes.add(pipe);
            x += PIPE_WIDTH * 3;
        }
    }

    public void update() 
    {
    	Iterator<Pipe> iterator = pipes.iterator();
    while (iterator.hasNext()) {
        Pipe pipe = iterator.next();
        pipe.move();
        if (pipe.isOutOfScreen()) {
            iterator.remove(); // Usar el método remove() del iterador
            int newX = pipes.get(pipes.size() - 1).getX() + PIPE_WIDTH * 3;
            int gapY = (int) (Math.random() * (FlappyBird_Panel.HEIGHT - PIPE_GAP));
            pipes.add(new Pipe(newX, gapY, PIPE_GAP));
        }
    }
    }

    public void draw(java.awt.Graphics g) {
        for (Pipe pipe : pipes) {
            pipe.draw(g);
        }
    }

    public List<Pipe> getPipes() {
        return pipes;
    }
}
