package mandelbrot;

import java.awt.Color;

import javax.swing.JPanel;

/**
 * Main class to run the program and display the GUI.
 * 
 * @author 170008965
 *
 */
public class GUIDisplay extends JPanel {

    /**
     * Default serial version UID;
     */
    private static final long serialVersionUID = 1L;
    public static final int WIDTH = 800;
    public static final int HEIGHT = 800;
    
    public static final Color PURPLE = new Color(255, 0, 255);

    @SuppressWarnings("unused")
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Model model = new Model();
        GuiDelegate gui = new GuiDelegate(model);
    }

}
