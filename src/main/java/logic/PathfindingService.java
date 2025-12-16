package logic;

import entities.Entity;

import java.util.List;
import java.util.Map;

public class PathfindingService {
    private final Map<Coordinates, Entity> worldMap;
    private final int width;
    private final int height;

    public PathfindingService(Map<Coordinates, Entity> worldMap, int width, int height) {
        this.worldMap = worldMap;
        this.width = width;
        this.height = height;
    }

    public List<Coordinates> findPath(Coordinates start, Coordinates target) {
        return AStar.getInstance().findPath(start, target, worldMap, width, height);
    }
}
