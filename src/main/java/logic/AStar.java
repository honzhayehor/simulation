package logic;

import enviroment.Cell;
import enviroment.WorldMap;
import graph.Graph;
import pathfinding.CoordinateMap;

import java.util.*;
import java.util.stream.Collectors;

public class AStar implements Pathfinder{
    private pathfinding.AStar algorithm;
    private final WorldMap worldMap;
    private final Map<Cell, Graph.Node> nodeIndex = new HashMap<>();
    private final Map<Graph.Node, Cell> reverseIndex = new HashMap<>();
    private final Map<Graph.Node, List<Graph.Node>> incomingIndex = new HashMap<>();
    private final Graph graph = Graph.create();
    private static final int BLOCKED_WEIGHT = 999;
    private static final int DEFAULT_WEIGHT = 1;

    public AStar(WorldMap map) {
        worldMap = map;
        CoordinateMap coordinateMap = new CoordinateMap();
        createGraphFromCellsAndPopulateCoordinateMap(graph, worldMap, coordinateMap, worldMap.asList());
        setAlgorithm(graph, coordinateMap);
    }

    private void setAlgorithm(Graph graph, CoordinateMap coordinateMap) {
        algorithm = new pathfinding.AStar(graph, coordinateMap);
    }

    @Override
    public List<Cell> findPath(Cell currentPos, Cell endPos) {
        Objects.requireNonNull(currentPos, "currentPos cannot be null");
        Objects.requireNonNull(endPos, "endPos cannot be null");

        Graph.Node from = nodeIndex.get(currentPos);
        Graph.Node to = nodeIndex.get(endPos);

        if (from == null || to == null) return List.of();

        updateGraphWeights();

        return algorithm.findPath(from, to)
                .stream()
                .map(reverseIndex::get)
                .collect(Collectors.toList());
    }

    private void updateGraphWeights() {
        for (Map.Entry<Cell, Graph.Node> entry : nodeIndex.entrySet()) {
            Cell cell = entry.getKey();
            Graph.Node node = entry.getValue();

            boolean isBlocked = !worldMap.suggestMove(cell);
            int weight = isBlocked ? BLOCKED_WEIGHT : DEFAULT_WEIGHT;

            updateIncomingEdgeWeights(node, weight);
        }
    }

    private void updateIncomingEdgeWeights(Graph.Node node, int weight) {
        List<Graph.Node> incoming = incomingIndex.getOrDefault(node, List.of());
        incoming.forEach(source ->
                graph.changeDistanceBetweenNodes(source, node, weight)
        );
    }

    private void createGraphFromCellsAndPopulateCoordinateMap(
            Graph graph,
            WorldMap worldMap,
            CoordinateMap coordinateMap,
            List<Cell> cells
    ) {
        int width = worldMap.getWidth();
        int height = worldMap.getHeight();

        for (Cell cell : cells) {
            Graph.Node node = graph.createNode(cell.toString());
            addNodeToCoordinateMapWithCellCoordinates(coordinateMap, node, cell);
            nodeIndex.put(cell, node);
            reverseIndex.put(node, cell);
        }

        int[][] directions = {{0,-1}, {0,1}, {-1,0}, {1,0}};

        connectNeighborNodes(directions, cells, width, height);

    }

    private void connectNeighborNodes(int[][] directions, List<Cell> cells, int width, int height) {
        for (Cell cell: cells) {
            Graph.Node current = nodeIndex.get(cell);

            for (int[] dir: directions) {
                int nx = cell.x() + dir[0];
                int ny = cell.y() + dir[1];

                if (nx >= 0 && nx < width && ny >= 0 && ny < height) {
                    Cell neighborKey = new Cell(nx, ny);
                    Graph.Node neighbor = nodeIndex.get(neighborKey);
                    if (neighbor != null && !graph.checkAdjacency(current, neighbor)) {
                        graph.connectNodes(current, neighbor, 1);
                        incomingIndex.computeIfAbsent(neighbor, k -> new ArrayList<>()).add(current);
                    }
                }
            }
        }
    }

    private void addNodeToCoordinateMapWithCellCoordinates(CoordinateMap coordinateMap, Graph.Node node, Cell cell) {
        Objects.requireNonNull(coordinateMap, "CoordinateMap cannot be null");
        Objects.requireNonNull(cell, "Cell cannot be null");
        if (graph.containsNode(node)) {
            coordinateMap.add(node, cell.x(), cell.y());
        }
    }
}
