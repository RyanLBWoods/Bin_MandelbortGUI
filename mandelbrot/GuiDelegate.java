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
import java.util.Stack;

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
 * Class to render relevant state information stored in the model and make changes to the model state based on user events. 
 * 
 * @author 170008965
 *
 */
public class GuiDelegate {

    private static final int FRAME_HEIGHT = 1000;
    private static final int FRAME_WIDTH = 1000;
    private static final int TEXT_WIDTH = 10;
    
    private JFrame frame;
    
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
    
    private Point begin;
    private Rectangle rect;
    
    private double mousePx;
    private double mousePy;
    private double mouseRx;
    private double mouseRy;

    /**
     * Instantiate GuiDelegate object
     * @param model the Model to observe, render, and update according to user events
     */
    public GuiDelegate(Model model){
        this.model = model;
        this.frame = new JFrame();  // set up the main frame for this GUI
        menu = new JMenuBar();
        toolbar = new JToolBar();
        inputField = new JTextField(TEXT_WIDTH);
        inputField.setText("50");
        
        this.record = new Record();
        setupComponents();
        
    }


    
    /**
     * Initialises the toolbar to contain the buttons, label, input field, etc. and adds the toolbar to the main frame.
     * Listeners are created for the buttons and text field which translate user events to model object method calls (controller aspect of the delegate) 
     */
    private void setupToolbar(){
        undo = new JButton("Undo");
        undo.addActionListener(new ActionListener(){     // to translate event for this button into appropriate model method call
            public void actionPerformed(ActionEvent e){
                // should  call method in model class if you want it to affect model
                
                if(record.undo.isEmpty()){
                    JOptionPane.showMessageDialog(frame, "Cannot Undo");
                } else {
                    record.addRedo(model);
                    model = new Model();
                    model = record.undo.pop();
                    inputField.setText(String.valueOf(model.getMaxIterations()));
                    updatePanel();
                }
                
            }
        });
        redo = new JButton("Redo");
        redo.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                // should  call method in model class if you want it to affect model
                if(record.redo.isEmpty()){
                    JOptionPane.showMessageDialog(frame, "Cannot Redo");
                } else {
                    record.addUndo(model);
                    model = new Model();
                    model = record.redo.pop();
                    inputField.setText(String.valueOf(model.getMaxIterations()));
                    updatePanel();
                }
                
            }
        });
        
        reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                record.undo = new Stack<>();
                record.redo = new Stack<>();
                model = new Model();
                model.reset();
                inputField.setText(String.valueOf(model.getMaxIterations()));
                updatePanel();
            }
        });
        
        red = new JButton("Red");
        red.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                record.addUndo(model);
                int currentIteration = model.getMaxIterations();
                model = new Model();
                model.changeIteration(currentIteration);
                model.setColor(Color.RED);
                inputField.setText(String.valueOf(model.getMaxIterations()));
                updatePanel();
            }
        });
        
        green = new JButton("Green");
        green.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                record.addUndo(model);
                int currentIteration = model.getMaxIterations();
                model = new Model();
                model.changeIteration(currentIteration);
                model.setColor(Color.GREEN);
                inputField.setText(String.valueOf(model.getMaxIterations()));
                updatePanel();
            }
        });
        
        blue = new JButton("Blue");
        blue.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                record.addUndo(model);
                int currentIteration = model.getMaxIterations();
                model = new Model();
                model.changeIteration(currentIteration);
                model.setColor(Color.BLUE);
                inputField.setText(String.valueOf(model.getMaxIterations()));
                updatePanel();
            }
        });
        
        white = new JButton("White");
        white.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                record.addUndo(model);
                int currentIteration = model.getMaxIterations();
                model = new Model();
                model.changeIteration(currentIteration);
                model.setColor(Color.WHITE);
                inputField.setText(String.valueOf(model.getMaxIterations()));
                updatePanel();
            }
        });

        purple = new JButton("Purple");
        purple.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                record.addUndo(model);
                int currentIteration = model.getMaxIterations();
                model = new Model();
                model.changeIteration(currentIteration);
                model.setColor(GUIDisplay.PURPLE);
                inputField.setText(String.valueOf(model.getMaxIterations()));
                updatePanel();
            }
        });
        
        JLabel label = new JLabel("Iterations: ");
        
        inputField.addKeyListener(new KeyListener(){        // to translate key event for the text filed into appropriate model method call
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    record.addUndo(model);
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
        
        apply = new JButton("Apply");       // to translate event for this button into appropriate model method call
        apply.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                record.addUndo(model);
                Color currentColor = model.getColor();
                model = new Model();
                model.changeIteration(Integer.valueOf(inputField.getText()));
                model.setColor(currentColor);
                updatePanel();
            }
        });

        // add buttons, label, and textfield to the toolbar
        toolbar.add(red);
        toolbar.add(green);
        toolbar.add(blue);
        toolbar.add(white);
        toolbar.add(purple);
        toolbar.addSeparator();
        toolbar.add(undo);
        toolbar.add(redo);
        toolbar.add(reset);
        toolbar.addSeparator();
        toolbar.add(label);
        toolbar.add(inputField);
        toolbar.add(apply);
        // add toolbar to north of main frame
        frame.add(toolbar, BorderLayout.NORTH);
    }

    
    /**
     * Sets up File menu with Load and Save entries
     * The Load and Save actions would normally be translated to appropriate model method calls similar to the way the code does this 
     * above in @see #setupToolbar(). However, load and save functionality is not implemented here, instead the code below merely displays 
     * an error message. 
     */ 
    private void setupMenu(){
        JMenu file = new JMenu ("File");
        JMenuItem load = new JMenuItem ("Load");
        JMenuItem save = new JMenuItem ("Save");
        file.add (load);
        file.add (save);
        menu.add (file);
        load.addActionListener(new ActionListener(){ 
            public void actionPerformed(ActionEvent e) {
                // should call appropriate method in model class if you want it to do something useful
                JFileChooser fc = new JFileChooser();
                fc.setMultiSelectionEnabled(false);
                fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                File dir = new File(System.getProperty("user.dir"));
                fc.setCurrentDirectory(dir);
                int returnVal = fc.showOpenDialog(fc);
                if(returnVal == JFileChooser.APPROVE_OPTION){
                    
                    File f = fc.getSelectedFile();
                    try {
                        System.out.println("File is " + f.toString());
                        FileInputStream fis = new FileInputStream(f);
                        ObjectInputStream ois = new ObjectInputStream(fis);
                        
                        model = new Model();
                        model = (Model) ois.readObject();
                        updatePanel();
                        
                        record.clear();
                        
                        ois.close();
                        fis.close();
                    } catch (Exception e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            }
        });
        save.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                // should call appropriate method in model class if you want it to do something useful
                JFileChooser fc = new JFileChooser();
                fc.setMultiSelectionEnabled(false);
                fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                File dir = new File(System.getProperty("user.dir"));
                fc.setCurrentDirectory(dir);
                int returnVal = fc.showOpenDialog(null);
                if(returnVal == JFileChooser.APPROVE_OPTION){
                    File f = fc.getSelectedFile();
                    try {
                        OutputStream os = new FileOutputStream(f);
                        BufferedOutputStream buf = new BufferedOutputStream(os);
                        ObjectOutputStream oos = new ObjectOutputStream(buf);
                        oos.writeObject(model);
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
        // add menubar to frame
        frame.setJMenuBar(menu);
    }
    
    /**
     * Method to setup the menu and toolbar components  
     */
    private void setupComponents(){
        setupMenu();
        setupToolbar();
        
        panel = new DrawPanel(model);
        panel.addMouseListener(new MouseListener() {
            
            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub
                
                    
                    
                if(e.getButton() == MouseEvent.BUTTON1){
                    Graphics2D g2 = (Graphics2D) panel.getGraphics();
                    g2.setXORMode(Color.WHITE);
                    g2.draw(rect);
                    g2.setPaintMode();
                    g2.dispose();
                    begin = null;
                    rect = null;
                    System.out.println("Mouse released at " + e.getX() + ", " + e.getY());
                    mouseRx = (double) e.getX();
                    mouseRy = (double) e.getY();
                    
                    if(mousePx > mouseRx){
                        double temp = mousePx;
                        mousePx = mouseRx;
                        mouseRx = temp;
                    }
                    
                    if(mousePy > mouseRy){
                        double temp = mousePy;
                        mousePy = mouseRy;
                        mouseRy = temp;
                    }
                    getNewLocation(mousePx, mousePy, mouseRx, mouseRy);
                    }
                
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub
                begin = e.getPoint();
                if(e.getButton() == MouseEvent.BUTTON1){
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
                System.out.println(panel.getX());
                System.out.println(panel.getY());
                System.out.println(frame.getX());
                System.out.println(frame.getY());
                if(e.getButton() == MouseEvent.BUTTON3 && e.getClickCount() == 2){
                    try {
                        File jpg = new File("MandelbrotSet.jpg");
                        OutputStream os = new FileOutputStream(jpg);
                        BufferedImage img = new Robot().createScreenCapture(new Rectangle(frame.getX(), frame.getY() + 90, panel.getWidth(), panel.getHeight()));
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
                Graphics2D g2 = (Graphics2D) panel.getGraphics();
                
                g2.setXORMode(Color.WHITE);
                
                if(rect != null){
                    g2.draw(rect);
                }
                
                rect = new Rectangle((int)Math.min(begin.getX(), e.getX()), (int)Math.min(begin.getY(), e.getY()), (int)Math.abs(begin.getX() - e.getX()), (int)Math.abs(begin.getY() - e.getY()));
                g2.draw(rect);
                g2.setPaintMode();
                g2.dispose();
            }
        });
        frame.setTitle("Mandelbort Set Display");
        frame.add(panel, BorderLayout.CENTER);
        frame.setSize (FRAME_WIDTH, FRAME_HEIGHT);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public void getNewLocation(double px, double py, double rx, double ry){
        System.out.println("Zoom from [" + px + ", " + py + "] to [" + rx + ", " + ry + "]");
        double originMinR = model.getMinReal();
        double originMaxR = model.getMaxReal();
        double originMinI = model.getMinImaginary();
        double originMaxI = model.getMaxImaginary();
        
        double originR = originMaxR - originMinR;
        double originI = originMaxI - originMinI;
        
        double newMinR = originMinR + ( px / model.getXResolution() * originR);
        double newMaxR = originMinR + ( rx / model.getXResolution() * originR);
        double newMinI = originMinI + ( py / model.getYResolution() * originI);
        double newMaxI = originMinI + ( ry / model.getYResolution() * originI);
        
        record.addUndo(model);
        Color currentColor = model.getColor();
        model = new Model();
        model.setNewData(newMinR, newMaxR, newMinI, newMaxI);
        model.setColor(currentColor);
        updatePanel();
        
    }
    
    public void updatePanel(){
        panel.updateModel(model);
        panel.repaint();
    }

}
