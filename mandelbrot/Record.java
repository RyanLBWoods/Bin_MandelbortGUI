package mandelbrot;

import java.util.Stack;

/**
 * Class to store operations for undo and redo function. This class use stack as
 * it is a LIFO data structure so when calling the pop function, it will pass
 * the latest model as a parameter.
 * 
 * @author 170008965
 *
 */
public class Record {

    Stack<Model> undo;
    Stack<Model> redo;

    /**
     * Initiate record.
     */
    public Record() {
        undo = new Stack<>();
        redo = new Stack<>();
    }

    /**
     * Method to add model to undo stack.
     * 
     * @param model
     *            Current model
     */
    public void addUndo(Model model) {
        this.undo.add(model);
    }

    /**
     * Method to add model to redo stack
     * 
     * @param model
     *            The model removed from undo stack
     */
    public void addRedo(Model model) {
        this.redo.add(model);
    }

    /**
     * Method to clear the record.
     */
    public void clear() {
        // TODO Auto-generated method stub
        this.undo.clear();
        this.redo.clear();
    }
}
