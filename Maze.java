import java.io.*;
import java.util.*;

public class Maze {
    //class variables to represent the maze structure, entrance, exit, and other necessary data
    private Graph graph;
    private GraphNode entrance, exit;
    private int coins;
    private Map<GraphNode, GraphNode> predecessorMap;
    private Map<GraphNode, Integer> coinsMap;

    //constructor: reads the maze structure from an input file and initializes the graph
    public Maze(String inputFile) throws MazeException {
        parseInputFile(inputFile);
    }

    //returns the graph object representing the maze. throws exception if the graph is null
    public Graph getGraph() throws MazeException {
        if (this.graph == null) {
            throw new MazeException("Graph is null.");
        }
        return this.graph;
    }

    //parses the input file to build the maze's graph structure
    private void parseInputFile(String inputFile) throws MazeException {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            int scaleFactor = Integer.parseInt(reader.readLine().trim());
            int width = Integer.parseInt(reader.readLine().trim());
            int length = Integer.parseInt(reader.readLine().trim());
            coins = Integer.parseInt(reader.readLine().trim());

            //initialize graph and maze structure
            graph = new Graph(width * length);
            char[][] mazeStructure = new char[length * 2 - 1][];

            for (int i = 0; i < mazeStructure.length; i++) {
                String line = reader.readLine();
                if (line == null) {
                    throw new MazeException("Unexpected end of file while reading maze structure.");
                }
                mazeStructure[i] = line.toCharArray();
            }

            processMazeStructure(mazeStructure, width, length);
        } catch (FileNotFoundException e) {
            throw new MazeException("File not found: " + inputFile + " - " + e.getMessage());
        } catch (IOException e) {
            throw new MazeException("Error reading the file: " + e.getMessage());
        } catch (NumberFormatException e) {
            throw new MazeException("Error parsing integer from the file: " + e.getMessage());
        } catch (Exception e) {
            throw new MazeException("Unexpected error while reading the file: " + e.getMessage());
        }
    }


    //processes the structure of the maze from the input file and builds the graph
    private void processMazeStructure(char[][] mazeStructure, int width, int length) throws MazeException {
        try {
            for (int i = 0; i < mazeStructure.length; i++) {
                //check if the line length matches the expected width
                if (mazeStructure[i].length != width * 2 - 1) {
                    throw new MazeException("Incorrect line length at line: " + (i + 1));
                }

                for (int j = 0; j < mazeStructure[i].length; j++) {
                    //process only 'c' or digit characters
                    if (mazeStructure[i][j] == 'c' || Character.isDigit(mazeStructure[i][j])) {
                        int nodeIndex1 = (i / 2) * width + j / 2;
                        int nodeIndex2 = (i % 2 == 0) ? nodeIndex1 + 1 : nodeIndex1 + width;
                        GraphNode node1 = graph.getNode(nodeIndex1);
                        GraphNode node2 = graph.getNode(nodeIndex2);
                        int edgeType = Character.isDigit(mazeStructure[i][j]) ? Character.getNumericValue(mazeStructure[i][j]) : 0;
                        String edgeLabel = edgeType > 0 ? "door" : "corridor";

                        graph.insertEdge(node1, node2, edgeType, edgeLabel);
                    }

                    //check for entrance and exit regardless of position
                    if (mazeStructure[i][j] == 's') {
                        entrance = graph.getNode((i / 2) * width + j / 2);
                    } else if (mazeStructure[i][j] == 'x') {
                        exit = graph.getNode((i / 2) * width + j / 2);
                    }
                }
            }

            //check if entrance and exit are properly set
            if (entrance == null || exit == null) {
                throw new MazeException("Entrance or exit not properly set in the maze.");
            }

        } catch (GraphException e) {
            throw new MazeException("Error processing the maze structure: " + e.getMessage());
        }
    }


    //solves the maze by finding a path from the entrance to the exit, if it exists
    public Iterator<GraphNode> solve() throws MazeException {
        try {
            graph.clearMarks();
            Stack<GraphNode> stack = new Stack<>();
            predecessorMap = new HashMap<>();
            coinsMap = new HashMap<>();

            if (entrance == null) {
                throw new MazeException("Entrance node is null.");
            }

            stack.push(entrance);
            coinsMap.put(entrance, coins);

            while (!stack.isEmpty()) {
                GraphNode current = stack.peek();
                if (current == null) {
                    throw new MazeException("Current node is null after peeking the stack.");
                }
                current.mark(true);

                if (current.equals(exit)) {
                    return constructPath(entrance, exit).iterator();
                }

                boolean moved = false;
                Iterator<GraphEdge> edges = graph.incidentEdges(current);
                while (edges.hasNext()) {
                    GraphEdge edge = edges.next();
                    GraphNode next = edge.opposite(current);

                    if (next == null) {
                        throw new MazeException("Opposite node is null for edge " + edge.getLabel());
                    }

                    if (!next.isMarked() && canTraverse(edge, current)) {
                        stack.push(next);
                        predecessorMap.put(next, current);
                        coinsMap.put(next, coinsMap.get(current) - (edge.getLabel().equals("door") ? edge.getType() : 0));
                        moved = true;
                        break;
                    }
                }

                if (!moved) {
                    stack.pop();
                    if (!stack.isEmpty()) {
                        current = stack.peek();
                        coins = coinsMap.get(current);
                    }
                }
            }
        } catch (GraphException e) {
            throw new MazeException("Error solving the maze: " + e.getMessage());
        }

        //return null to indicate no solution is found
        return null;
    }

    //helper method to determine if an edge can be traversed based on the number of available coins
    private boolean canTraverse(GraphEdge edge, GraphNode currentNode) {
        int requiredCoins = edge.getType();
        return edge.getLabel().equals("corridor") || (edge.getLabel().equals("door") && coinsMap.get(currentNode) >= requiredCoins);
    }

    //constructs the path from the entrance to the exit of the maze
    private List<GraphNode> constructPath(GraphNode start, GraphNode end) {
        LinkedList<GraphNode> path = new LinkedList<>();
        for (GraphNode at = end; at != null; at = predecessorMap.get(at)) {
            path.addFirst(at);
        }
        return path;
    }
}
