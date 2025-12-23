package logic;

import entities.Entity;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Map;

public class PathfindingService {
    private final Map<Cell, Entity> worldMap;
    private final int width;
    private final int height;

    public PathfindingService(Map<Cell, Entity> worldMap, int width, int height) {
        this.worldMap = worldMap;
        this.width = width;
        this.height = height;
    }

    public Deque<Cell> findPath(Cell start, Cell target) {
        List<Cell> path = AStar.getInstance()
                .findPath(start, target, worldMap, width, height);

        return path == null || path.isEmpty()
                ? new ArrayDeque<>()
                : new ArrayDeque<>(path);
    }
}
