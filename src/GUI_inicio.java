import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.InputStream;

public class GUI_inicio extends JComponent implements KeyListener {
    private static final long serialVersionUID = 1L;
    private int selectedOption = 0;
    private String[] options = {"Jugar", "Mejores Tiempos", "Opciones", "Salir"};
    private ImageIcon[] icons = {
            new ImageIcon("jugar.png"),
            new ImageIcon("mejores-tiempos.png"),
            new ImageIcon("opciones.png"),
            new ImageIcon("salir.png")
    };
    private Image backgroundImage;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        GUI_inicio menu = new GUI_inicio();
        frame.add(menu);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        menu.requestFocusInWindow();
    }

    public GUI_inicio() {
        setPreferredSize(new Dimension(800, 600));
        setFocusable(true);
        addKeyListener(this);

        // Cargar la imagen de fondo
        try {
            backgroundImage = ImageIO.read(getClass().getResourceAsStream("/textures/Fon.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Cargar la fuente "Press Start 2P"
        try {
            InputStream stream = getClass().getResourceAsStream("/textures/PressStart2P.ttf");
            Font pressStartFont = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(Font.PLAIN, 24);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(pressStartFont);
            setFont(pressStartFont);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dibujar la imagen de fondo
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }

        // Dibujar el título
        g.setColor(Color.WHITE);
        g.setFont(new Font("Press Start 2P", Font.BOLD, 24));
        g.drawString("FlappyBird by: chocobollos", 100, 100);

        // Dibujar las opciones del menú
        for (int i = 0; i < options.length; i++) {
            // Dibujar el indicador ">"
            if (i == selectedOption) {
                g.setColor(Color.RED);
                g.drawString(">", 260, 358 + 50 * i);
            }

            // Dibujar el texto de la opción
            g.setColor(Color.WHITE);
            g.drawString(options[i], 300, 360 + 50 * i);

            // Dibujar el icono
            if (i < icons.length) {
                Image icon = icons[i].getImage();
                g.drawImage(icon, 200, 200 + 50 * i, this);
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_DOWN) {
            selectedOption = (selectedOption + 1) % options.length;
            repaint();
        } else if (key == KeyEvent.VK_UP) {
            selectedOption = (selectedOption - 1 + options.length) % options.length;
            repaint();
        } else if (key == KeyEvent.VK_ENTER) {
            selectOption();
        }
    }

    private void selectOption() {
        switch (selectedOption) {
            case 0:
            SwingUtilities.invokeLater(() -> {
                JFrame frame = new JFrame("Flappy Bird");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setResizable(false);

                FlappyBird_Panel panel = new FlappyBird_Panel();
                frame.getContentPane().add(panel);

                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            });
                break;
            case 1:
            JOptionPane.showMessageDialog(null, "Mejores Tiempos");
                break;
            case 2:
            JOptionPane.showMessageDialog(null, "Acción Opciones");
                break;
            case 3:
                System.exit(0);
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
