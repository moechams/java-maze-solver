public class GraphNode {
    //variables to store the name of the node and its marked status
    private int name;
    private boolean marked;

    //constructor: initializes a graph node with the given name and sets its marked status to false
    public GraphNode(int name) {
        this.name = name;
        this.marked = false;
    }

    //marks the node with the specified boolean value
    public void mark(boolean mark) {
        this.marked = mark;
    }

    //returns the marked status of the node
    public boolean isMarked() {
        return this.marked;
    }

    //returns the name of the node
    public int getName() {
        return this.name;
    }
}
