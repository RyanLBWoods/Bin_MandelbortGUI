package mandelbrot;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;

/**
 * Class to render relevant state information stored in the model and make
 * changes to the model state based on user events.
 * 
 * @author 170008965
 *
 */
public class GuiDelegate {

    /**
     * Default frame size.
     */
    private static final int FRAME_HEIGHT = 1000;
    private static final int FRAME_WIDTH = 1000;
    private static final int TEXT_WIDTH = 10;
    // Main Frame
    private JFrame frame;
    // Components
    private JToolBar toolbar;
    private JTextField inputField;
    private JButton undo;
    private JButton redo;
    private JButton reset;
    private JButton red;
    private JButton green;
    private JButton blue;
    private JButton white;
    private JButton purple;
    private JButton apply;
    private JMenuBar menu;

    private Model model;
    private DrawPanel panel;
    private Record record;
    // Point location and rectangle for drawing the selection frame
    private Point begin;
    private Rectangle rect;
    // Mouse pressed location and released location
    private double mousePx;
    private double mousePy;
    private double mouseRx;
    private double mouseRy;

    /**
     * Instantiate GuiDelegate object
     * 
     * @param model
     *            the Model to observe, render, and update according to user
     *            events
     */
    public GuiDelegate(Model model) {
        this.model = model;
        // set up the main frame for this GUI
        this.frame = new JFrame();
        menu = new JMenuBar();
        toolbar = new JToolBar();
        inputField = new JTextField(TEXT_WIDTH);
        inputField.setText(String.valueOf(model.getMaxIterations()));

        this.record = new Record();
        setupComponents();
    }

    /**
     * Initialises the toolbar to contain the buttons, label, input field, etc.
     * and adds the toolbar to the main frame.
     */
    private void setupToolbar() {
        // Set undo button
        undo = new JButton("Undo");
        undo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (record.undo.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Cannot Undo");
                } else {
                    // Save current model to redo stack
                    record.addRedo(model);
                    // Update model
                    model = new Model();
                    model = record.undo.pop();
                    inputField.setText(String.valueOf(model.getMaxIterations()));
                    updatePanel();
                }

            }
        });
        // Set redo button
        redo = new JButton("Redo");
        redo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (record.redo.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Cannot Redo");
                } else {
                    // Save current model to undo stack
                    record.addUndo(model);
                    // Update model
                    model = new Model();
                    model = record.redo.pop();
                    inputField.setText(String.valueOf(model.getMaxIterations()));
                    updatePanel();
                }

            }
        });
        // Set reset button
        reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                // Clean the record
                record.clear();
                // Update model
                model = new Model();
                model.reset();
                inputField.setText(String.valueOf(model.getMaxIterations()));
                updatePanel();
            }
        });
        // Set red color button
        red = new JButton("Red");
        red.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                // Save current model to undo stack
                record.addUndo(model);
                int currentIteration = model.getMaxIterations();
                // Update model
                model = new Model();
                model.changeIteration(currentIteration);
                model.setColor(Color.RED);
                inputField.setText(String.valueOf(model.getMaxIterations()));
                updatePanel();
            }
        });
        // Set green color button
        green = new JButton("Green");
        green.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                // Save current model to undo stack
                record.addUndo(model);
                int currentIteration = model.getMaxIterations();
                // Update model
                model = new Model();
                model.changeIteration(currentIteration);
                model.setColor(Color.GREEN);
                inputField.setText(String.valueOf(model.getMaxIterations()));
                updatePanel();
            }
        });
        // Set blue color button
        blue = new JButton("Blue");
        blue.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                // Save current model to undo stack
                record.addUndo(model);
                int currentIteration = model.getMaxIterations();
                // Update model
                model = new Model();
                model.changeIteration(currentIteration);
                model.setColor(Color.BLUE);
                inputField.setText(String.valueOf(model.getMaxIterations()));
                updatePanel();
            }
        });
        // Set white color button
        white = new JButton("White");
        white.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                // Save current model to undo stack
                record.addUndo(model);
                int currentIteration = model.getMaxIterations();
                // Update model
                model = new Model();
                model.changeIteration(currentIteration);
                model.setColor(Color.WHITE);
                inputField.setText(String.valueOf(model.getMaxIterations()));
                updatePanel();
            }
        });
        // Set purple color button
        purple = new JButton("Purple");
        purple.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                // Save current model to undo stack
                record.addUndo(model);
                int currentIteration = model.getMaxIterations();
                // Update model
                model = new Model();
                model.changeIteration(currentIteration);
                model.setColor(GUIDisplay.PURPLE);
                inputField.setText(String.valueOf(model.getMaxIterations()));
                updatePanel();
            }
        });
        // Set iteration choose
        JLabel label = new JLabel("Iterations: ");
        inputField.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    // Save current model to undo stack
                    record.addUndo(model);
                    // Update model
                    model = new Model();
                    model.changeIteration(Integer.valueOf(inputField.getText()));
                    updatePanel();
                }
            }

            public void keyReleased(KeyEvent e) {
            }

            public void keyTyped(KeyEvent e) {
            }
        });
        // Set confirm iteration selection button
        apply = new JButton("Apply");
        apply.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Save current model to undo stack
                record.addUndo(model);
                Color currentColor = model.getColor();
                // Update model
                model = new Model();
                model.changeIteration(Integer.valueOf(inputField.getText()));
                model.setColor(currentColor);
                updatePanel();
            }
        });

        // add buttons, label, and text field to the tool bar
        toolbar.add(red);
        toolbar.add(green);
        toolbar.add(blue);
        toolbar.add(white);
        toolbar.add(purple);
        // Add a separator
        toolbar.addSeparator();
        toolbar.add(undo);
        toolbar.add(redo);
        toolbar.add(reset);
        // Add a separator
        toolbar.addSeparator();
        toolbar.add(label);
        toolbar.add(inputField);
        toolbar.add(apply);
        // add tool bar to north of main frame
        frame.add(toolbar, BorderLayout.NORTH);
    }

    /**
     * Method to set up File menu with Load and Save entries.
     */
    private void setupMenu() {
        JMenu file = new JMenu("File");
        JMenuItem load = new JMenuItem("Load");
        JMenuItem save = new JMenuItem("Save");
        file.add(load);
        file.add(save);
        menu.add(file);
        // Set load button
        load.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open a file chooser
                JFileChooser fc = new JFileChooser();
                fc.setMultiSelectionEnabled(false);
                fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                File dir = new File(System.getProperty("user.dir"));
                fc.setCurrentDirectory(dir);
                int returnVal = fc.showOpenDialog(fc);
                if (returnVal == JFileChooser.APPROVE_OPTION) {

                    File f = fc.getSelectedFile();
                    try {
                        System.out.println("File is " + f.toString());
                        FileInputStream fis = new FileInputStream(f);
                        ObjectInputStream ois = new ObjectInputStream(fis);
                        // Re-initiate the model
                        model = new Model();
                        // Load the model object
                        model = (Model) ois.readObject();
                        updatePanel();
                        // Clean the record after loading
                        record.clear();
                        // Close streams
                        ois.close();
                        fis.close();
                    } catch (Exception e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            }
        });
        // Set save button
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Open a file chooser
                JFileChooser fc = new JFileChooser();
                fc.setMultiSelectionEnabled(false);
                fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                File dir = new File(System.getProperty("user.dir"));
                fc.setCurrentDirectory(dir);
                int returnVal = fc.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File f = fc.getSelectedFile();
                    try {
                        OutputStream os = new FileOutputStream(f);
                        BufferedOutputStream buf = new BufferedOutputStream(os);
                        ObjectOutputStream oos = new ObjectOutputStream(buf);
                        // Save the model object
                        oos.writeObject(model);
                        // Close streams
                        oos.close();
                        buf.close();
                        os.close();
                    } catch (Exception e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            }
        });
        // add menu bar to frame
        frame.setJMenuBar(menu);
    }

    /**
     * Method to setup the menu and tool bar components
     */
    private void setupComponents() {
        setupMenu();
        setupToolbar();
        // Set panel
        panel = new DrawPanel(model);
        // Add mouse listener to panel
        panel.addMouseListener(new MouseListener() {

            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub
                // Only call method if it is pressed by mouse's left button
                if (e.getButton() == MouseEvent.BUTTON1) {
                    Graphics2D g = (Graphics2D) panel.getGraphics();
                    g.setXORMode(Color.WHITE);
                    g.draw(rect);
                    g.setPaintMode();
                    g.dispose();
                    begin = null;
                    rect = null;
                    System.out.println("Mouse released at " + e.getX() + ", " + e.getY());
                    mouseRx = (double) e.getX();
                    mouseRy = (double) e.getY();
                    // Get the usable x and y position
                    if (mousePx > mouseRx) {
                        double temp = mousePx;
                        mousePx = mouseRx;
                        mouseRx = temp;
                    }

                    if (mousePy > mouseRy) {
                        double temp = mousePy;
                        mousePy = mouseRy;
                        mouseRy = temp;
                    }
                    // Call zooming method
                    getNewLocation(mousePx, mousePy, mouseRx, mouseRy);
                }

            }

            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub
                begin = e.getPoint();
                // Only call method if it is pressed by mouse's left button
                if (e.getButton() == MouseEvent.BUTTON1) {
                    System.out.println("Mouse pressed at " + e.getX() + ", " + e.getY());
                    mousePx = (double) e.getX();
                    mousePy = (double) e.getY();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub
                // When double clicked with right button, store the image to a
                // jpg file
                if (e.getButton() == MouseEvent.BUTTON3 && e.getClickCount() == 2) {
                    try {
                        File jpg = new File("MandelbrotSet.jpg");
                        OutputStream os = new FileOutputStream(jpg);
                        BufferedImage img = new Robot().createScreenCapture(
                                new Rectangle(frame.getX(), frame.getY() + 90, panel.getWidth(), panel.getHeight()));
                        ImageIO.write(img, "jpg", os);
                    } catch (AWTException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            }
        });

        panel.addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseMoved(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseDragged(MouseEvent e) {
                // TODO Auto-generated method stub
                // Draw a selection frame
                Graphics2D g = (Graphics2D) panel.getGraphics();
                g.setXORMode(Color.WHITE);
                // Get rid of previous selection frame
                if (rect != null) {
                    g.draw(rect);
                }
                // Draw the frame
                rect = new Rectangle((int) Math.min(begin.getX(), e.getX()), (int) Math.min(begin.getY(), e.getY()),
                        (int) Math.abs(begin.getX() - e.getX()), (int) Math.abs(begin.getY() - e.getY()));
                g.draw(rect);
                g.setPaintMode();
                g.dispose();
            }
        });
        // Setup main frame
        frame.setTitle("Mandelbort Set Display");
        frame.add(panel, BorderLayout.CENTER);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Method to calculate data for zooming.
     * 
     * @param px
     *            Mouse pressed X position
     * @param py
     *            Mouse pressed Y position
     * @param rx
     *            Mouse released X position
     * @param ry
     *            Mouse released Y position
     */
    public void getNewLocation(double px, double py, double rx, double ry) {
        System.out.println("Zoom from [" + px + ", " + py + "] to [" + rx + ", " + ry + "]");
        // Get original data
        double originMinR = model.getMinReal();
        double originMaxR = model.getMaxReal();
        double originMinI = model.getMinImaginary();
        double originMaxI = model.getMaxImaginary();
        // Range of real and imaginary
        double originR = originMaxR - originMinR;
        double originI = originMaxI - originMinI;
        // Calculate zoomed data
        double newMinR = originMinR + (px / model.getXResolution() * originR);
        double newMaxR = originMinR + (rx / model.getXResolution() * originR);
        double newMinI = originMinI + (py / model.getYResolution() * originI);
        double newMaxI = originMinI + (ry / model.getYResolution() * originI);
        // Add current model to undo stack
        record.addUndo(model);
        Color currentColor = model.getColor();
        // Update model
        model = new Model();
        model.setNewData(newMinR, newMaxR, newMinI, newMaxI);
        model.setColor(currentColor);
        updatePanel();
    }

    /**
     * Method to update panel.
     */
    public void updatePanel() {
        panel.updateModel(model);
        panel.repaint();
    }

}
