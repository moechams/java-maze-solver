import java.util.*;

public class Graph implements GraphADT {
    //list of nodes in the graph
    private final ArrayList<GraphNode> nodes;
    //adjacency list to store edges for each node
    private final Map<GraphNode, ArrayList<GraphEdge>> adjacencyList;

    //constructor: initializes a graph with the specified number of nodes
    public Graph(int numNodes) {
        nodes = new ArrayList<>(numNodes);
        adjacencyList = new HashMap<>();

        //create nodes and add to the graph
        for (int i = 0; i < numNodes; i++) {
            GraphNode newNode = new GraphNode(i);
            nodes.add(newNode);
            adjacencyList.put(newNode, new ArrayList<>());
        }
    }

    //inserts an edge between two nodes in the graph
    @Override
    public void insertEdge(GraphNode u, GraphNode v, int type, String label) throws GraphException {
        if (!nodes.contains(u) || !nodes.contains(v)) {
            throw new GraphException("One or more nodes do not exist.");
        }

        GraphEdge newEdge = new GraphEdge(u, v, type, label);
        adjacencyList.get(u).add(newEdge);
        adjacencyList.get(v).add(newEdge);
    }

    //retrieves a node from the graph by its name
    @Override
    public GraphNode getNode(int name) throws GraphException {
        for (GraphNode node : nodes) {
            if (node.getName() == name) {
                return node;
            }
        }
        throw new GraphException("Node with specified name does not exist.");
    }

    //returns an iterator to all edges incident on a node
    @Override
    public Iterator<GraphEdge> incidentEdges(GraphNode u) throws GraphException {
        if (!adjacencyList.containsKey(u)) {
            throw new GraphException("Node does not exist.");
        }
        return adjacencyList.get(u).iterator();
    }

    //retrieves an edge between two specified nodes
    @Override
    public GraphEdge getEdge(GraphNode u, GraphNode v) throws GraphException {
        if (!adjacencyList.containsKey(u) || !adjacencyList.containsKey(v)) {
            throw new GraphException("One or more nodes do not exist.");
        }

        for (GraphEdge edge : adjacencyList.get(u)) {
            if (edge.secondEndpoint().equals(v)) {
                return edge;
            }
        }

        throw new GraphException("Edge does not exist.");
    }

    //checks if two nodes are adjacent in the graph
    @Override
    public boolean areAdjacent(GraphNode u, GraphNode v) throws GraphException {
        if (!adjacencyList.containsKey(u) || !adjacencyList.containsKey(v)) {
            throw new GraphException("One or more nodes do not exist.");
        }

        for (GraphEdge edge : adjacencyList.get(u)) {
            if (edge.secondEndpoint().equals(v) || edge.firstEndpoint().equals(v)) {
                return true;
            }
        }

        return false;
    }

    //clears the marks on all nodes in the graph
    public void clearMarks() {
        for (GraphNode node : nodes) {
            node.mark(false);
        }
    }
}
