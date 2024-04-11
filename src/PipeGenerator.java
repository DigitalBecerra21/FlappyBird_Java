import java.awt.Color;

public class PipeGenerator implements Runnable {
    private FlappyBird_Panel panel;
    private int initialPosition;
    private String DebugString;

    public PipeGenerator(FlappyBird_Panel panel, int initialPosition, String DebugString) 
    {
        this.panel = panel;
        this.initialPosition = initialPosition;
        this.DebugString = DebugString;
    }

    @Override
    public void run() {
        while (true) {
            int gapY = (int) (Math.random() * (FlappyBird_Panel.HEIGHT - 200)) + 50;
            int gapHeight = 150;
            int width = 80 ;
            int timeBetweenPipes = 7000;
            
            Color color = Color.GREEN;

            Pipe pipe = new Pipe(this.initialPosition, gapY, gapHeight, width, color, this.DebugString);
            panel.addPipe(pipe);

            try {
                Thread.sleep(timeBetweenPipes); // Adjust this for pipe generation speed
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
