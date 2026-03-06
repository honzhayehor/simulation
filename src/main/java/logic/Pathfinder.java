package logic;

import graph.Graph;
import pathfinding.AStar;

public class Pathfinder {
    private final Graph worldGraph;
    private AStar algorithm;

    public Pathfinder() {
        worldGraph = Graph.create();
    }
}
