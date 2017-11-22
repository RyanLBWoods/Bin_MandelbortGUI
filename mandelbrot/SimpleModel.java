package mandelbrot;

import java.util.Observable;

/**
 * A simple model class whose purpose is to store text entered by a user.
 * 
 * The model's state is altered by user input when the delegate calls the addText()
 * method below. 
 * 
 * The model extends the Observable class and is observed by the Delegate class. This form
 * of loose coupling permits the Delegate (View) to be updated when the model has changed.
 * 
 * @author jonl
 *
 */
public class SimpleModel extends Observable {

    private static final int DEFAULT_WIDTH = 1000;
    private static final int DEFAULT_HEIGHT = 1000;
    
    private MandelbrotCalculator mc;
    private int[][] mcdata;
    
    private int xR, yR, maxIterations;
    private double minR, maxR, minImg, maxImg, radiusSquared;

    /**
     * Constructs a new SimpleModel instance.
     * Initialises the StringBuffer.
     */
    public SimpleModel(){
        this.xR = DEFAULT_WIDTH;
        this.yR = DEFAULT_HEIGHT;
        this.minR = MandelbrotCalculator.INITIAL_MIN_REAL;
        this.maxR = MandelbrotCalculator.INITIAL_MAX_REAL;
        this.minImg = MandelbrotCalculator.INITIAL_MIN_IMAGINARY;
        this.maxImg = MandelbrotCalculator.INITIAL_MAX_IMAGINARY;
        this.maxIterations = MandelbrotCalculator.INITIAL_MAX_ITERATIONS;
        this.radiusSquared = MandelbrotCalculator.DEFAULT_RADIUS_SQUARED;
        
        this.mc = new MandelbrotCalculator();
        this.mcdata = mc.calcMandelbrotSet(xR, yR, minR, maxR, minImg, maxImg, maxIterations, radiusSquared);
    }
    
    public void setNewData(double newMinR, double newMaxR, double newMinI, double newMaxI){
        this.minR = newMinR;
        this.maxR = newMaxR;
        this.minImg = newMinI;
        this.maxImg = newMaxI;
        this.mc = new MandelbrotCalculator();
        this.mcdata = mc.calcMandelbrotSet(xR, yR, minR, maxR, minImg, maxImg, maxIterations, radiusSquared);
    }
    
    public int[][] getMandelbrot_data() {
        return mcdata;
    }

    public int getXResolution() {
        return xR;
    }

    public int getYResolution() {
        return yR;
    }

    public double getMinReal() {
        return minR;
    }

    public double getMaxReal() {
        return maxR;
    }

    public double getMinImaginary() {
        return minImg;
    }

    public double getMaxImaginary() {
        return maxImg;
    }

    public int getMaxIterations() {
        return maxIterations;
    }

    public double getRadiusSquared() {
        return radiusSquared;
    }
    
}