package mandelbrot;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class BasicDisplay extends JComponent {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    
    private BufferedImage bi;
    
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        new BasicDisplay();
    }
    
    
    
    BasicDisplay(){
        
        bi = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        
        JFrame frame = new JFrame("Mandelbort Set");
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.getContentPane().add(this);
        frame.pack();
        frame.setVisible(true);
        
    }
    
    public void render(){
        MandelbrotCalculator mc = new MandelbrotCalculator();
        for(int x = 0; x < WIDTH; x++){
            for(int y = 0; y < HEIGHT; y ++){
//                int color = mc.calcMandel(cReal, cImaginary, maxIterations, radiusSquared);
            }
        }
    }
    
    @Override
    public void addNotify() {
        // TODO Auto-generated method stub
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }
    
    @Override
    public void paint(Graphics g) {
        // TODO Auto-generated method stub
        g.drawImage(bi, 0, 0, null);
    }

}
