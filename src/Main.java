import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Main implements Runnable {

    //FRAME
    private JFrame frame;
    private static Canvas canvas;
    private static JPanel panel;

    int width = 800;
    int height = 800;

    int[][] sandpiles = new int[width][height];


    public Main() {
        frame = new JFrame("Sorting Algorithms");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout());
        frame.add(canvas = new Canvas());
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        new Thread(this).start();

    }

    private void render() {
        BufferStrategy bs = canvas.getBufferStrategy();
        if (bs == null) {
            canvas.createBufferStrategy(2);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int num = sandpiles[x][y];
                g.setColor(new Color(255, 0, 0));
                if (num == 0) {
                    g.setColor(new Color(255, 255, 0));
                    g.drawLine(x, y, x, y);
                } else if (num == 1) {
                    g.setColor(new Color(0, 185, 63));
                    g.drawLine(x, y, x, y);
                } else if (num == 2) {
                    g.setColor(new Color(0, 104, 255));
                    g.drawLine(x, y, x, y);
                } else if (num == 3) {
                    g.setColor(new Color(122, 0, 229));
                    g.drawLine(x, y, x, y);
                }
            }
        }
        g.dispose();
        bs.show();

    }

    private void update() {
        sandpiles[width / 2][height / 2] = 1000000000;
        int[][] nextpiles = new int[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int num = sandpiles[x][y];
                if (num < 4) {
                    nextpiles[x][y] = sandpiles[x][y];
                }
            }
        }
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int num = sandpiles[i][j];
                if (num >= 4) {
                    nextpiles[i][j] += (num - 4);
                    if (i + 1 < width)
                        nextpiles[i + 1][j]++;
                    if (i - 1 >= 0)
                        nextpiles[i - 1][j]++;
                    if (j + 1 < height)
                        nextpiles[i][j + 1]++;
                    if (j - 1 >= 0)
                        nextpiles[i][j - 1]++;
                }
            }
        }

        sandpiles = nextpiles;
    }


    @Override
    public void run() {
        BasicTimer timer = new BasicTimer(6000);
        while (true) {
            timer.sync();
            render();
            for (int i = 0; i < 1000; i++) {
                update();
            }

        }
    }

    public static void main(String[] args) {
        new Main();
    }

}

