import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.net.URL;

public class FlappyBird extends JFrame {
    private Timer timer;
    private int birdY = 300;
    private int birdVelocity = 0;
    private int gravity = 1;
    private int score = 0;
    private boolean isGameOver = false;
    private JButton startButton;
    private ArrayList<Rectangle> pipes;
    private Random rand;

    private ImageIcon birdIcon;
    private ImageIcon pipeIcon;
    private ImageIcon bgIcon;

    public FlappyBird() {
        setTitle("Flappy Bird");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        pipes = new ArrayList<>(); 

        // Ruta de Imagenes
        
        birdIcon = new ImageIcon(getResourceURL("textures/bird.png"));
        birdIcon = new ImageIcon(birdIcon.getImage().getScaledInstance(40, 30, Image.SCALE_SMOOTH)); // escalar el bird
        pipeIcon = new ImageIcon(getResourceURL("textures/pipe.png"));
        bgIcon = new ImageIcon(getResourceURL("textures/bg.jpeg"));

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                
                g.drawImage(bgIcon.getImage(), 0, 0, getWidth(), getHeight(), null); // Ajustar el tamaño del fondo para que ocupe toda la ventana

                
                for (Rectangle pipe : pipes) {
                    g.drawImage(pipeIcon.getImage(), pipe.x, pipe.y, pipe.width, pipe.height, null);
                }

                
                g.drawImage(birdIcon.getImage(), 100, birdY, null);

                
                g.setColor(Color.black);
                g.setFont(new Font("Arial", Font.BOLD, 20));
                g.drawString("Score: " + score, 50, 50);

                if (isGameOver) {
                    g.drawString("Game Over", 350, 250);
                }
            }
        };
        panel.setLayout(null);
        panel.setFocusable(true);

        startButton = new JButton("Start");
        startButton.setBounds(350, 250, 100, 50);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });
        panel.add(startButton);

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                jump();
            }
        });

        add(panel);
        setVisible(true);

        rand = new Random();
    }

    private URL getResourceURL(String resourcePath) {
        return getClass().getClassLoader().getResource(resourcePath);
    }

    public void startGame() {
        isGameOver = false;
        birdY = 300;
        birdVelocity = 0;
        score = 0;
        startButton.setVisible(false);
        pipes.clear();
        timer = new Timer(20, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add new pipes and move existing pipes
                if (pipes.size() < 1) {
                    int heightTop = rand.nextInt(100) + 50;
                    int heightBottom = 600 - heightTop - 200;
                    pipes.add(new Rectangle(800, 0, 50, heightTop));
                    pipes.add(new Rectangle(800, 600 - heightBottom, 50, heightBottom));
                }
                for (int i = 0; i < pipes.size(); i++) {
                    Rectangle pipe = pipes.get(i);
                    pipe.x -= 5;
                    if (pipe.x + pipe.width < 0) {
                        pipes.remove(pipe);
                        i--;
                        score += 5; // Add score when a pipe is passed
                    }
                }

                // Check for collisions
                for (Rectangle pipe : pipes) {
                    if (pipe.intersects(new Rectangle(100, birdY, 50, 30))) {
                        gameOver();
                        break;
                    }
                }

                birdY += birdVelocity;
                birdVelocity += gravity;

                if (birdY > 500) {
                    gameOver();
                }

                if (birdY < 0) {
                    birdY = 0;
                    birdVelocity = 0;
                }

                repaint();
            }
        });
        timer.start();
    }

    public void jump() {
        if (!isGameOver) {
            birdVelocity = -10;
        }
    }

    public void gameOver() {
        isGameOver = true;
        timer.stop();
        startButton.setVisible(true);
        JOptionPane.showMessageDialog(this, "Game Over! Your score is: " + score);
    }

    public static void main(String[] args) {
        new FlappyBird();
    }
}