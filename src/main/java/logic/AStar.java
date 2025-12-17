package logic;

import entities.Entity;

import java.util.*;

public final class AStar {

    private static final AStar INSTANCE = new AStar();

    private AStar() {
    }

    public static AStar getInstance() {
        return INSTANCE;
    }

    public List<Cell> findPath(
            Cell start,
            Cell target,
            Map<Cell, Entity> worldMap,
            int width,
            int height
    ) {
        Objects.requireNonNull(start);
        Objects.requireNonNull(target);
        Objects.requireNonNull(worldMap);

        if (!isInside(start, width, height) || !isInside(target, width, height)) {
            return Collections.emptyList();
        }

        if (start.equals(target)) {
            return List.of(start);
        }

        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(Node::getF));
        Map<Cell, Node> allNodes = new HashMap<>();
        Set<Cell> closedSet = new HashSet<>();

        Node startNode = new Node(start, 0, heuristic(start, target), null);
        openSet.add(startNode);
        allNodes.put(start, startNode);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            if (closedSet.contains(current.position)) {
                continue;
            }

            if (current.position.equals(target)) {
                return reconstructPath(current);
            }

            closedSet.add(current.position);

            for (Cell neighbor : getNeighbors(current.position)) {
                if (!isInside(neighbor, width, height)) continue;
                if (!isWalkable(neighbor, worldMap) && !neighbor.equals(target)) continue;
                if (closedSet.contains(neighbor)) continue;

                int tentativeG = current.g + 1;

                Node node = allNodes.get(neighbor);
                if (node == null) {
                    node = new Node(neighbor, tentativeG, heuristic(neighbor, target), current);
                    allNodes.put(neighbor, node);
                    openSet.add(node);
                } else if (tentativeG < node.g) {
                    node.g = tentativeG;
                    node.parent = current;
                    openSet.add(node);
                }
            }
        }

        return Collections.emptyList();
    }

    // ===== helpers =====

    private boolean isInside(Cell c, int width, int height) {
        return c.x() >= 1 && c.x() <= width &&
                c.y() >= 1 && c.y() <= height;
    }

    private boolean isWalkable(Cell c, Map<Cell, Entity> worldMap) {
        Entity e = worldMap.get(c);
        return e == null || e.isWalkable();
    }

    private int heuristic(Cell a, Cell b) {
        return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
    }

    private List<Cell> getNeighbors(Cell c) {
        return List.of(
                new Cell(c.x() + 1, c.y()),
                new Cell(c.x() - 1, c.y()),
                new Cell(c.x(), c.y() + 1),
                new Cell(c.x(), c.y() - 1)
        );
    }

    private List<Cell> reconstructPath(Node node) {
        List<Cell> path = new ArrayList<>();
        for (Node n = node; n != null; n = n.parent) {
            path.add(n.position);
        }
        Collections.reverse(path);
        return path;
    }

    private static class Node {
        Cell position;
        int g;
        int h;
        Node parent;

        Node(Cell position, int g, int h, Node parent) {
            this.position = position;
            this.g = g;
            this.h = h;
            this.parent = parent;
        }

        int getF() {
            return g + h;
        }
    }
}
