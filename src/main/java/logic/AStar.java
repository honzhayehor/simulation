package logic;

import entities.Entity;

import java.util.*;

public class AStar {
    private final Map<Coordinates, Entity> worldMap;
    private final int width;
    private final int height;

    /**
     * Constructor for {@code A* algorithm}.
     * @param worldMap World map (that is a {@code WorldMap}). Needed so that the algorithm knows which way to go.
     * @param width Width of the world map (an {@code Integer}). Required for additional validation
     * @param height Height of the world map (an {@code Integer}).Required for additional validation
     */
    public AStar(Map<Coordinates, Entity> worldMap, int width, int height) {
        this.worldMap = worldMap;
        this.width = width;
        this.height = height;
    }

    /**
     * Method that returns list of coordinates, walking which will bring the object that called this method from position 'start' to position 'target' if exist
     * @param start Object of type {@code Coordinates}. That considerate the starting position of the algorithm
     * @param target Object of type {@code Coordinates}. That considerate the target position
     * @return {@code List<Coordinates>} if the path exists. Empty list if there is no possible path for the given arguments. If we already are on the target position return list with one element (which is the target and start {@code Coordinates})
     */

    public List<Coordinates> findPath(Coordinates start, Coordinates target) {
        // Basic prevalidation for start and target. They must not be null.
        Objects.requireNonNull(start, "Start cannot be null");
        Objects.requireNonNull(target, "Target cannot be null");

        // If the start or target are out of bound of the map, then returns empty list.
        if (!isInside(start) || !isInside(target)) {
            return Collections.emptyList();
        }

        // If the Entity already at the position 'target' - returns a list of one Coordinate (start)
        if (start.equals(target)) {
            return List.of(start);
        }

        // Queue of vertices that we yet to process. Ordered by f = g + h
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(Node::getF));

        // WorldMap for quick search of Nodes by coordinate
        Map<Coordinates, Node> allNodes = new HashMap<>();

        // Закрита множина (вершини, які вже оброблені)
        Set<Coordinates> closedSet = new HashSet<>();

        // Start node (no parent, heuristic of start and target)
        Node startNode = new Node(start, 0, heuristic(start, target), null);
        openSet.add(startNode);
        allNodes.put(start, startNode);

        // Main while-loop that iterates while the queue is not empty.
        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            // If we already has this position that was closed by more optimal route - skip
            if (closedSet.contains(current.position)) {
                continue;
            }

            // If reach the target - reconstruct the path
            if (current.position.equals(target)) {
                return reconstructPath(current);
            }

            closedSet.add(current.position);

            // Iterates through all the nearest neighbours
            for (Coordinates neighborPos : getNeighbors(current.position)) {
                if (!isInside(neighborPos)) {
                    continue;
                }

                if (!isWalkable(neighborPos) && !neighborPos.equals(target)) {
                    continue;
                }

                if (closedSet.contains(neighborPos)) {
                    continue;
                }

                int tentativeG = current.g + movementCost();

                Node neighbor = allNodes.get(neighborPos);
                if (neighbor == null) {
                    neighbor = new Node(
                            neighborPos,
                            tentativeG,
                            heuristic(neighborPos, target),
                            current
                    );
                    allNodes.put(neighborPos, neighbor);
                    openSet.add(neighbor);
                } else if (tentativeG < neighbor.g) {
                    // Found the path to this vertices
                    neighbor.g = tentativeG;
                    neighbor.parent = current;
                    // f will change, so adding it to openSet again
                    openSet.add(neighbor);
                }
            }
        }

        // Path is not found - return empty list.
        return Collections.emptyList();
    }

    /**
     * Private method, that used to validate if the passed {@code Coordinates} are in the scope of global {@code WorldMap}
     * @param c Object of type {@code Coordinates}
     * @return {@true} if the provided {@code Coordinates} are in scope of the world map.
     */
    private boolean isInside(Coordinates c) {
        return c.x() >= 1 && c.x() <= width &&
                c.y() >= 1 && c.y() <= height;
    }

    /**
     * Checks if the cell contains {@code Entity} that can be walked through
     * @param c Object of type {@code Coordinates} - cell that we want to validate
     * @return {@code true} if the {@code Entity} (or {@code null}) in that specific cell can be walked through
     */
    private boolean isWalkable(Coordinates c) {
        Entity e = worldMap.get(c);
        return e == null || !e.isWalkable();
    }

    /**
     * Just a method that sets the movement cost (kinda a scalar value for A* algorithm)
     * @return Cost of movement ({@code Integer})
     */
    private int movementCost() {
        return 1;
    }

    /**
     * Returns the heuristic for given pair of {@code Coordinates}
     * @param a First {@code Coordinates} (e.g. Start coordinate)
     * @param b Second {@code Coordinates} (e.g. Target coordinate)
     * @return {@code Integer}
     */
    private int heuristic(Coordinates a, Coordinates b) {
        return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
    }

    /**
     * Returns a list of neighbours for given cell ({@code Coordinates})
     * @param c Current cell
     * @return {@code List<Coordinates>}
     */
    private List<Coordinates> getNeighbors(Coordinates c) {
        List<Coordinates> neighbors = new ArrayList<>(4);
        neighbors.add(new Coordinates(c.x() + 1, c.y()));
        neighbors.add(new Coordinates(c.x() - 1, c.y()));
        neighbors.add(new Coordinates(c.x(), c.y() + 1));
        neighbors.add(new Coordinates(c.x(), c.y() - 1));
        return neighbors;
    }

    /**
     * Method to reconstruct the path when the path is found for given {@code Node}
     * @param node {@code Node}
     * @return {@code List<Coordinates>} - full path
     */
    private List<Coordinates> reconstructPath(Node node) {
        List<Coordinates> path = new ArrayList<>();
        Node current = node;
        while (current != null) {
            path.add(current.position);
            current = current.parent;
        }
        Collections.reverse(path);
        return path;
    }

    /**
     * Inner class of {@code Node} that represents a cell
     */
    private static class Node {
        private final Coordinates position;
        private int g;
        private int h;
        private Node parent;

        private Node(Coordinates position, int g, int h, Node parent) {
            this.position = position;
            this.g = g;
            this.h = h;
            this.parent = parent;
        }

        private int getF() {
            return g + h;
        }
    }
}


