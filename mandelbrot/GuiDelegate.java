package mandelbrot;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Observable;
import java.util.Observer;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;


/**
 * The SimpleGuiDelegate class whose purpose is to render relevant state information stored in the model and make changes to the model state based on user events. 
 * 
 * This class uses Swing to display the model state when the model changes. This is the view aspect of the delegate class. 
 * It also listens for user input events (in the listeners defined below), translates these to appropriate calls to methods
 * defined in the model class so as to make changes to the model. This is the controller aspect of the delegate class. 
 * The class implements Observer in order to permit it to be added as an observer of the model class. 
 * When the model calls notifyObservers() (after executing setChanged()) 
 * the update(...) method below is called in order to update the view of the model.
 * 
 * @author jonl
 *
 */
public class GuiDelegate implements Observer {

    private static final int FRAME_HEIGHT = 1000;
    private static final int FRAME_WIDTH = 1000;
    private static final int TEXT_HEIGHT = 10;
    private static final int TEXT_WIDTH = 10;
    
    private JFrame mainFrame;
    
    private JToolBar toolbar;
    private JTextField inputField;
    private JButton undo;
    private JButton redo;
    private JButton reset;
    private JScrollPane outputPane;
    private JTextArea outputField;
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
     * Instantiate a new SimpleGuiDelegate object
     * @param model the Model to observe, render, and update according to user events
     */
    public GuiDelegate(Model model){
        this.model = model;
        this.mainFrame = new JFrame();  // set up the main frame for this GUI
        menu = new JMenuBar();
        toolbar = new JToolBar();
        inputField = new JTextField(TEXT_WIDTH);
        inputField.setText("50");
        outputField = new JTextArea(TEXT_WIDTH, TEXT_HEIGHT);
        outputField.setEditable(false);
        outputPane = new JScrollPane(outputField);
        
        this.record = new Record();
        setupComponents();
        
        // add the delegate UI component as an observer of the model
        // so as to detect changes in the model and update the GUI view accordingly
        model.addObserver(this);
        
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
                    JOptionPane.showMessageDialog(mainFrame, "Cannot Undo");
                } else {
                    record.addRedo(model);
                    model = new Model();
                    model = record.undo.pop();
                    panel.changeModel(model);
                    panel.repaint();
                }
                
            }
        });
        redo = new JButton("Redo");
        redo.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                // should  call method in model class if you want it to affect model
                if(record.redo.isEmpty()){
                    JOptionPane.showMessageDialog(mainFrame, "Cannot Redo");
                } else {
                    record.addUndo(model);
                    model = new Model();
                    model = record.redo.pop();
                    panel.changeModel(model);
                    panel.repaint();
                }
                
            }
        });
        
        reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                inputField.setText("50");
                record.undo = new Stack<>();
                record.redo = new Stack<>();
                model = new Model();
                model.reset();
                panel.changeModel(model);
                panel.repaint();
            }
        });

        JLabel label = new JLabel("Iterations: ");
        
        inputField.addKeyListener(new KeyListener(){        // to translate key event for the text filed into appropriate model method call
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    record.addUndo(model);
                    model = new Model();
                    model.changeIteration(Integer.valueOf(inputField.getText()));
                    panel.changeModel(model);
                    panel.repaint();
                }
            }
            public void keyReleased(KeyEvent e) {
            }
            public void keyTyped(KeyEvent e) {
            }
        });
        
        JButton add_button = new JButton("Apply");       // to translate event for this button into appropriate model method call
        add_button.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
//                model.addText(inputField.getText());        // same as when user presses carriage return key, tell model to add text entered by user
//                inputField.setText("");                     // and clear the input box in the GUI view
                record.addUndo(model);
                model = new Model();
                model.changeIteration(Integer.valueOf(inputField.getText()));
                panel.changeModel(model);
//                record.addDone(model);
                panel.repaint();
            }
        });

        // add buttons, label, and textfield to the toolbar
        toolbar.add(undo);
        toolbar.add(redo);
        toolbar.add(reset);
        toolbar.add(label);
        toolbar.add(inputField);
        toolbar.add(add_button);
        // add toolbar to north of main frame
        mainFrame.add(toolbar, BorderLayout.NORTH);
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
                JOptionPane.showMessageDialog(mainFrame, "Ooops, Load not linked to model!");
            }
        });
        save.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                // should call appropriate method in model class if you want it to do something useful
                JOptionPane.showMessageDialog(mainFrame, "Ooops, Save not linked to model!");
            }
        });     
        // add menubar to frame
        mainFrame.setJMenuBar(menu);
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
            
            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub
                System.out.println("Mouse pressed at " + e.getX() + ", " + e.getY());
                mousePx = (double) e.getX();
                mousePy = (double) e.getY();
                begin = e.getPoint();
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
        mainFrame.setTitle("Mandelbort Set Display");
        mainFrame.add(panel, BorderLayout.CENTER);
        mainFrame.setSize (FRAME_WIDTH, FRAME_HEIGHT);
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }   
    
    /**
     * This method contains code to update the GUI view when the model changes
     * The method is called when the model changes (i.e. when the model executes setChanged() and notifyObservers())
     * Any parameters passed to notifyObservers @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     * are passed to update. 
     * The code in update should get hold of the model state it requires and update the relevant GUI components so that
     * an updated view of the model is displayed on screen.
     * For this simple example, the only state information we need from the model is what is in the model's text buffer and the
     * only GUI view element we need to update is the text area used for output.
     * 
     * NOTE: In a more complex program, the model may hold information on a variety of objects, such as various shapes, their positions, etc.
     * and the GUI view would then have to get hold of all that state info and produce a graphical representation of theses objects.
     * As a result, the update method would have to get hold of various bits of model state and then
     * call the relevant methods (defined in the GUI code) to render the objects.
     * 
     */
    public void update(Observable o, Object arg) {

        // Tell the SwingUtilities thread to update the GUI components.
        // This is safer than executing outputField.setText(model.getText()) 
        // in the caller's thread 
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                panel.repaint();
            }
        });
    }
    
    public void getNewLocation(double px, double py, double rx, double ry){
        System.out.println("Repaint from [" + px + ", " + py + "] to [" + rx + ", " + ry + "]");
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
        
        System.out.println(newMinR + ", " + newMaxR + "==");
        System.out.println(newMinI + ", " + newMaxI);
        
//        model = new Model();
        model.setNewData(newMinR, newMaxR, newMinI, newMaxI);
//        record.addDone(model);
        panel.repaint();
        
    }
    
}
