package mandelbrot;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * Class to draw the panel according to data from model.
 * 
 * @author 170008965
 *
 */
public class DrawPanel extends JPanel {

    /**
     * Default serial version UID
     */
    private static final long serialVersionUID = 1L;

    Model model;

    /**
     * Initiate panel.
     * 
     * @param model
     *            The model which provides data for panel to draw
     */
    public DrawPanel(Model model) {
        this.model = model;
    }

    /**
     * Method to change the model
     * 
     * @param model
     *            The model which provides data for panel to draw
     */
    public void updateModel(Model model) {
        this.model = model;
    }

    @Override
    protected void paintComponent(Graphics g) {
        // TODO Auto-generated method stub
        super.paintComponent(g);
        // Draw panel according to mandelbrot set
        int[][] mdata = model.getMandelbrot_data();
        int maxIt = model.getMaxIterations();
        for (int x = 0; x < mdata.length; x++) {
            for (int y = 0; y < mdata[x].length; y++) {
                if (mdata[x][y] < maxIt) {

                    // int current = mdata[x][y] / model.getMaxIterations();
                    float current = (float) mdata[x][y] / model.getMaxIterations();
                    // Draw color
                    if (model.getColor().equals(Color.RED)) {
                        g.setColor(new Color(current, 0, 0));
                    } else if (model.getColor().equals(Color.GREEN)) {
                        g.setColor(new Color(0, current, 0));
                    } else if (model.getColor().equals(Color.BLUE)) {
                        g.setColor(new Color(0, 0, current));
                    } else if (model.getColor().equals(GUIDisplay.PURPLE)) {
                        g.setColor(new Color(current, 0, current));
                    } else {
                        g.setColor(new Color(255, 255, 255));
                    }

                } else {
                    g.setColor(new Color(0, 0, 0));
                }
                // Draw a point as a pixel
                g.drawLine(y, x, y, x);
            }
        }
    }

}
