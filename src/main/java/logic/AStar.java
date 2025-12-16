package logic;

import entities.Entity;
import logic.Coordinates;

import java.util.*;

public final class AStar {

    private static final AStar INSTANCE = new AStar();

    private AStar() {
    }

    public static AStar getInstance() {
        return INSTANCE;
    }

    public List<Coordinates> findPath(
            Coordinates start,
            Coordinates target,
            Map<Coordinates, Entity> worldMap,
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
        Map<Coordinates, Node> allNodes = new HashMap<>();
        Set<Coordinates> closedSet = new HashSet<>();

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

            for (Coordinates neighbor : getNeighbors(current.position)) {
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

    private boolean isInside(Coordinates c, int width, int height) {
        return c.x() >= 1 && c.x() <= width &&
                c.y() >= 1 && c.y() <= height;
    }

    private boolean isWalkable(Coordinates c, Map<Coordinates, Entity> worldMap) {
        Entity e = worldMap.get(c);
        return e == null || e.isWalkable();
    }

    private int heuristic(Coordinates a, Coordinates b) {
        return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
    }

    private List<Coordinates> getNeighbors(Coordinates c) {
        return List.of(
                new Coordinates(c.x() + 1, c.y()),
                new Coordinates(c.x() - 1, c.y()),
                new Coordinates(c.x(), c.y() + 1),
                new Coordinates(c.x(), c.y() - 1)
        );
    }

    private List<Coordinates> reconstructPath(Node node) {
        List<Coordinates> path = new ArrayList<>();
        for (Node n = node; n != null; n = n.parent) {
            path.add(n.position);
        }
        Collections.reverse(path);
        return path;
    }

    private static class Node {
        Coordinates position;
        int g;
        int h;
        Node parent;

        Node(Coordinates position, int g, int h, Node parent) {
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
