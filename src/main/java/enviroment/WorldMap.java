package enviroment;

import units.abstraction.Entity;

import java.util.*;

public final class WorldMap {
    private final Map<Cell, Set<Entity>> map;
    public WorldMap(int width, int height) {
        map = initMap(width, height);
    }

    public WorldMap() {
        map = initMap(10, 10);
    }

    private Map<Cell, Set<Entity>> initMap(int xLength, int yLength) {
        Map<Cell, Set<Entity>> wm = new HashMap<>();
        if (xLength < 0 || yLength < 0) {
            throw new IllegalArgumentException("x or y cannot be null");
        }
        for (int i = 0; i < xLength; i++) {
            for (int j = 0; j < yLength; j++) {
                wm.put(new Cell(i, j), new HashSet<>());
            }
        }
        return wm;
    }

    public boolean cellContainsEntity(Cell cell) {
        if (cell == null) throw new IllegalArgumentException("Cell cannot be null");
        Set<Entity> list = map.get(cell);
        return !list.isEmpty();
    }

    public boolean cellContainsEntity(int coordinateX, int coordinateY) {
        if (coordinateX <= 0 || coordinateY <= 0)
            throw new IllegalArgumentException("Coordinates could not be negative or zero");

        Cell cell = findCellByCoordinates(coordinateX, coordinateY);
        if (cell == null) throw new IllegalArgumentException("Cell not found");

        return cellContainsEntity(cell);
    }

    private Cell findCellByCoordinates(int x, int y) {
        Cell key = new Cell(x, y);
        return map.containsKey(key) ? key : null;
    }

    public void addEntityToCell(Cell cell, Entity entity) {
        if (cell == null || entity == null) {
            throw new IllegalArgumentException("Cell and entity cannot be null");
        }
        Set<Entity> entities = map.get(cell);
        if (entities == null) {
            throw new IllegalArgumentException("Cell not found: " + cell);
        }
        entities.add(entity);
    }

    public boolean suggestMove(Cell cell) {
        Set<Entity> entities = map.get(cell);
        if (entities == null) throw new IllegalArgumentException("Cell not found: " + cell);
        return entities.stream().allMatch(Entity::isPassable);
    }

    public Cell findCellOfEntity(Entity entity) {
        return map.entrySet().stream()
                .filter(entry -> entry.getValue().contains(entity))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
    }
}
