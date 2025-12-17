package logic;

import entities.Entity;

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

    public List<Cell> findPath(Cell start, Cell target) {
        return AStar.getInstance().findPath(start, target, worldMap, width, height);
    }
}
