package mandelbrot;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUIDisplay extends JPanel {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 800;

    private BufferedImage bi;

    public static void main(String[] args) {
        // TODO Auto-generated method stub
//        new GUIDisplay();
        SimpleModel model = new SimpleModel();
        SimpleGuiDelegate gui = new SimpleGuiDelegate(model);
    }

//    @Override
//    protected void paintComponent(Graphics g) {
//        // TODO Auto-generated method stub
//        super.paintComponent(g);
//        MandelbrotCalculator mc = new MandelbrotCalculator();
//        int[][] data = mc.calcMandelbrotSet(WIDTH, HEIGHT, MandelbrotCalculator.INITIAL_MIN_REAL,
//                MandelbrotCalculator.INITIAL_MAX_REAL, MandelbrotCalculator.INITIAL_MIN_IMAGINARY,
//                MandelbrotCalculator.INITIAL_MAX_IMAGINARY, MandelbrotCalculator.INITIAL_MAX_ITERATIONS,
//                MandelbrotCalculator.DEFAULT_RADIUS_SQUARED);
//        for (int x = 0; x < data.length; x++) {
//            for (int y = 0; y < data[x].length; y++) {
//                System.out.println(data[x][y]);
//                if (data[x][y] >= MandelbrotCalculator.INITIAL_MAX_ITERATIONS) {
//                    g.setColor(new Color(0, 0, 0));
//                    g.drawLine(y, x, y, x);
//
//                } else {
//                     g.setColor(new Color(255, 255, 255));
//                     g.drawLine(y, x, y, x);
//                }
//            }
//        }
//    }

//    GUIDisplay() {
//
//        // bi = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
//
//        JFrame frame = new JFrame("Mandelbort Set");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(WIDTH, HEIGHT);
//        frame.getContentPane().add(this);
//        frame.setVisible(true);
//
//    }

}
