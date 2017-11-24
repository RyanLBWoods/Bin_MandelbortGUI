package mandelbrot;

import java.awt.Color;
import java.io.Serializable;

/**
 * Model class to operate with the Mandelbrot set.
 * 
 * @author 170008965
 *
 */
public class Model implements Serializable {

    /**
     * Generated serial version UID.
     */
    private static final long serialVersionUID = 7855006198479249120L;
    /**
     * Default resolution for model.
     */
    private static final int DEFAULT_WIDTH = 1000;
    private static final int DEFAULT_HEIGHT = 1000;

    /**
     * Model's attributes.
     */
    private MandelbrotCalculator mc;
    private int[][] mcdata;

    private int xR, yR, maxIterations;
    private double minR, maxR, minImg, maxImg, radiusSquared;

    private Color color;

    /**
     * Initiate model.
     */
    public Model() {
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
        this.color = Color.WHITE;
    }

    /**
     * Method to set new values to attributes and re-calculate Mandelbrot set.
     * 
     * @param newMinR
     *            New minimum Real value
     * @param newMaxR
     *            New maximum Real value
     * @param newMinI
     *            New minimum Imaginary value
     * @param newMaxI
     *            New maximum Imaginary value
     */
    public void setNewData(double newMinR, double newMaxR, double newMinI, double newMaxI) {
        this.minR = newMinR;
        this.maxR = newMaxR;
        this.minImg = newMinI;
        this.maxImg = newMaxI;

        System.out.println("New MinReal: " + minR);
        System.out.println("New MaxReal: " + maxR);
        System.out.println("New MinImaginary: " + minImg);
        System.out.println("New MaxImaginary: " + maxImg);
        this.mc = new MandelbrotCalculator();
        this.mcdata = mc.calcMandelbrotSet(xR, yR, minR, maxR, minImg, maxImg, maxIterations, radiusSquared);
    }

    /**
     * Method to change the max iterations
     * 
     * @param newIterations
     *            Number of new max iterations
     */
    public void changeIteration(int newIterations) {
        this.maxIterations = newIterations;
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

    /**
     * Method to reset model.
     */
    public void reset() {
        // TODO Auto-generated method stub
        this.minR = MandelbrotCalculator.INITIAL_MIN_REAL;
        this.maxR = MandelbrotCalculator.INITIAL_MAX_REAL;
        this.minImg = MandelbrotCalculator.INITIAL_MIN_IMAGINARY;
        this.maxImg = MandelbrotCalculator.INITIAL_MAX_IMAGINARY;
        this.maxIterations = MandelbrotCalculator.INITIAL_MAX_ITERATIONS;
        this.radiusSquared = MandelbrotCalculator.DEFAULT_RADIUS_SQUARED;

        this.mc = new MandelbrotCalculator();
        this.mcdata = mc.calcMandelbrotSet(xR, yR, minR, maxR, minImg, maxImg, maxIterations, radiusSquared);
    }

    public Color getColor() {
        return color;
    }

    /**
     * Method to set color.
     * 
     * @param color
     *            The color to set
     */
    public void setColor(Color color) {
        // TODO Auto-generated method stub
        this.color = color;
    }

}