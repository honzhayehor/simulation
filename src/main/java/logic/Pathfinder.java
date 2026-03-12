package logic;

import enviroment.Cell;
import enviroment.WorldMap;
import graph.Graph;
import pathfinding.AStar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pathfinder {
    private final AStar algorithm;
    private final WorldMap worldMap;
    private final Graph graph;

    public Pathfinder(AStar aStar, WorldMap map) {
        algorithm = aStar;
        worldMap = map;
        this.graph = Graph.create();
    }

    public List<Cell> findPath(Cell currentPos, Cell endPos) {
        List<Cell> cells = worldMap.asList();
        return List.of();
    }

    private void updateGraph(List<Cell> cells) {
        // Yet to be implemented
    }

    private void createGraphFromCells(List<Cell> cells) {
        int width = worldMap.getWidth();
        int height = worldMap.getHeight();

        Map<Cell, Graph.Node> nodeIndex = new HashMap<>();

        for (Cell cell: cells) {
            Graph.Node node = graph.createNode(cell.toString());
            nodeIndex.put(cell, node);
        }

        int[][] directions = {{0,-1}, {0,1}, {-1,0}, {1,0}};

        for (Cell cell: cells) {
            Graph.Node current = nodeIndex.get(cell);

            for (int[] dir: directions) {
                int nx = cell.x() + dir[0];
                int ny = cell.y() + dir[1];
                Cell neighborKey = new Cell(nx, ny);

                if (nx >= 0 && nx < width && ny >= 0 && ny < height) {
                    Graph.Node neighbor = nodeIndex.get(neighborKey);
                    if (neighbor != null && !graph.checkAdjacency(current, neighbor)) {
                        graph.connectNodes(current, neighbor, 1);
                    }
                }
            }
        }
    }
}
