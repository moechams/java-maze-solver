public class GraphEdge {
    //endpoints of the edge
    private GraphNode u;
    private GraphNode v;
    private int type;
    private String label;

    //constructor: initializes an edge with specified endpoints, type, and label
    public GraphEdge(GraphNode u, GraphNode v, int type, String label) {
        this.u = u;
        this.v = v;
        this.type = type;
        this.label = label;
    }

    //returns the first endpoint of the edge
    public GraphNode firstEndpoint() {
        return this.u;
    }

    //returns the second endpoint of the edge
    public GraphNode secondEndpoint() {
        return this.v;
    }

    //returns the type of the edge
    public int getType() {
        return this.type;
    }

    //sets the type of the edge
    public void setType(int newType) {
        this.type = newType;
    }

    //returns the label of the edge
    public String getLabel() {
        return this.label;
    }

    //sets the label of the edge
    public void setLabel(String newLabel) {
        this.label = newLabel;
    }

    //returns the node opposite to the given node on this edge
    public GraphNode opposite(GraphNode node) {
        if (node.equals(u)) {
            return v;
        } else if (node.equals(v)) {
            return u;
        } else {
            throw new IllegalArgumentException("Node is not incident to this edge.");
        }
    }
}
