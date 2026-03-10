package logic;

import enviroment.Cell;
import graph.Graph;
import pathfinding.AStar;
import units.abstraction.Entity;

import java.util.List;

public class Pathfinder {
    private final Graph worldGraph;
    private final AStar algorithm;

    public Pathfinder() {
        worldGraph = Graph.create();
        algorithm = new AStar(worldGraph);
    }

    public List<Cell> findPath(Cell cell) {
        // TODO complete this method to utilize Astar algorithm
        return List.of();
    }
}
