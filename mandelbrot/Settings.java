package mandelbrot;

import java.awt.Color;

public class Settings {

    private int xR, yR, maxIterations;
    private double minR, maxR;
    private double minImg, maxImg;
    private double radiusSquared;
    private Color color;
    
    public Settings(Model model){
        this.xR = model.getXResolution();
        this.yR = model.getYResolution();
        this.minR = model.getMinReal();
        this.maxR = model.getMaxReal();
        this.minImg = model.getMinImaginary();
        this.maxImg = model.getMaxImaginary();
        this.maxIterations = model.getMaxIterations();
        this.radiusSquared = model.getRadiusSquared();
        this.color = model.getColor();
    }
    
    public int getXRSetting(){
        return xR;
    }
    
    public int getYRSetting(){
        return yR;
    }
    
    public double getMinRealSetting() {
        return minR;
    }

    public double getMaxRealSetting() {
        return maxR;
    }

    public double getMinImaginarySetting() {
        return minImg;
    }

    public double getMaxImaginarySetting() {
        return maxImg;
    }

    public int getMaxIterationsSetting() {
        return maxIterations;
    }

    public double getRadiusSquaredSetting() {
        return radiusSquared;
    }
    public Color getColorSetting(){
        return color;
    }
}
