package mandelbrot;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Panel extends JPanel{

    SimpleModel model;
    
    public Panel(SimpleModel model){
        this.model = model;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        // TODO Auto-generated method stub
        super.paintComponent(g);
        int[][] mdata = model.getMandelbrot_data();
        int maxIt = model.getMaxIterations();
        for(int x = 0; x < mdata.length; x++){
            for(int y = 0; y < mdata.length; y++){
                if(mdata[x][y] < maxIt){
                    g.setColor(new Color(255, 255, 255));
                    g.drawLine(y, x, y, x);
                } else {
                    g.setColor(new Color(0, 0, 0));
                    g.drawLine(y, x, y, x);
                }
            }
        }
    }
    
}
