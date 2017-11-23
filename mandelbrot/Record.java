package mandelbrot;

import java.util.ArrayList;
import java.util.Stack;

public class Record {

    Stack<Model> undo;
    Stack<Model> redo;
    
//    ArrayList<Model> done;
//    ArrayList<Model> undo;
    
    public Record() {
        undo = new Stack<>();
        redo = new Stack<>();
    }
    
    public void addUndo(Model model){
        this.undo.add(model);
    }
    
    public void addRedo(Model model){
        this.redo.add(model);
    }
}
