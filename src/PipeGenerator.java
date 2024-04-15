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
    	while (!Thread.currentThread().isInterrupted()) {
            try {
            	                           //Fórmula para generar un int entre [Altura del panel-200 y 200]
                int gapY = (int) (Math.random() * ((FlappyBird_Panel.HEIGHT - 200) - (200) +1)) + 200;
                int gapHeight = 150;
                int width = 80;
                int timeBetweenPipes = 7000;

                Pipe pipe = new Pipe(this.initialPosition, gapY, gapHeight, width, this.DebugString);
                panel.addPipe(pipe);

                Thread.sleep(timeBetweenPipes);
            } catch (InterruptedException e) {
                // Manejar la interrupción
                Thread.currentThread().interrupt(); // Restaura la bandera de interrupción
                break; // Salir del bucle
            }
        }
    }
}
