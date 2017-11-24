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

    public Record() {
        undo = new Stack<>();
        redo = new Stack<>();
    }

    public void addUndo(Model model) {
        this.undo.add(model);
    }

    public void addRedo(Model model) {
        this.redo.add(model);
    }
}
