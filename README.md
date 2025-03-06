# Java Maze Solver

A graph-based maze solving application that finds paths through mazes with doors requiring coins to open.

## Overview

This project implements a maze solver using graph theory concepts. The maze is represented as an undirected graph where:
- Nodes represent rooms
- Edges represent corridors or doors between rooms
- Doors require a specified number of coins to open

The program reads a maze description from an input file and uses a modified depth-first search algorithm to find a path from the entrance to the exit while optimizing coin usage.

## Features

- Graph-based pathfinding with a custom graph implementation
- Supports various maze structures with corridors and doors
- Resource management (coins) during pathfinding
- Handles complex maze layouts with multiple possible paths

## Implementation Details

The project consists of several key components:

- **GraphNode**: Represents a room in the maze
- **GraphEdge**: Represents a corridor or door between rooms
- **Graph**: Implements the graph data structure with adjacency lists
- **Maze**: Handles maze parsing, visualization, and solving

The solving algorithm uses a modified depth-first search that tracks available resources (coins) to determine which paths are viable.

## How It Works

1. The maze is loaded from a text file with a specific format
2. Each room becomes a node in the graph
3. Corridors and doors are represented as edges with different types
4. A modified DFS algorithm finds a path from entrance to exit
5. Doors can only be traversed if enough coins are available

## Usage

```
java Solve inputFile.txt
```

Where `inputFile.txt` is a text file describing the maze structure.

## Input File Format

The input file follows this format:
```
S       // Scale factor for display
A       // Width of the maze
L       // Length of the maze
k       // Number of available coins
RHRHR...// Maze structure
VWVWV...
...
```

Special characters in the maze structure:
- 's': entrance
- 'x': exit
- 'o': room
- 'c': corridor
- 'w': wall
- '0'-'9': door requiring that many coins to open

## Example

For a sample maze with the following structure:
```
30
4
3
4
s1owo1o
cwcwcwc
o2o3oco
ww4wcwc
ococx3o
```

The solver will find a path from the entrance to exit while using at most 4 coins.

## Skills Demonstrated

- Graph theory and graph traversal algorithms
- Object-oriented design principles
- Resource-constrained path finding
- Java collections framework
- Exception handling
