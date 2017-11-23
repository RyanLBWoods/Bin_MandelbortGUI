package mandelbrot;

import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class GUIDisplay extends JPanel {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 800;

    private BufferedImage bi;

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Model model = new Model();
        GuiDelegate gui = new GuiDelegate(model);
    }

}
