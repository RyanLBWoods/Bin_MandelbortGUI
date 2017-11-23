package mandelbrot;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class DrawPanel extends JPanel{

    Model model;
    
    public DrawPanel(Model model){
        this.model = model;
    }
    
    public void changeModel(Model model){
        this.model = model;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        // TODO Auto-generated method stub
        super.paintComponent(g);
        int[][] mdata = model.getMandelbrot_data();
        int maxIt = model.getMaxIterations();
        for(int x = 0; x < mdata.length; x++){
            for(int y = 0; y < mdata[x].length; y++){
                if(mdata[x][y] < maxIt){
                    float current = (float) mdata[x][y] / model.getMaxIterations();
                    if(model.getColor().equals(Color.BLUE)){
                        g.setColor(new Color(0, 0, current));
                    } else if (model.getColor().equals(Color.RED)){
                        g.setColor(new Color(current, 0, 0));
                    } else {
                        g.setColor(new Color(255, 255, 255));
                    }
                    
                } else {
                    g.setColor(new Color(0, 0, 0));
                }
                g.drawLine(y, x, y, x);
            }
        }
    }
    
}
