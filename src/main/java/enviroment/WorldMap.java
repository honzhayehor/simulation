package enviroment;

import units.abstraction.Creature;
import units.abstraction.Entity;

import java.util.*;

public final class WorldMap {
    private final Map<Cell, Set<Entity>> map;
    private int width;
    private int height;

    public WorldMap(int width, int height) {
        this.width = width;
        this.height = height;
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
    private record SearchBoundaries(int x, int y, int minX, int maxX, int minY, int maxY) {}

    private SearchBoundaries getSquareBoundaries(Cell cell, int vision) {
        int x = cell.x();
        int y = cell.y();
        return new SearchBoundaries(
                x, y,
                Math.max(0, x - vision),
                Math.min(width - 1, x + vision),
                Math.max(0, y - vision),
                Math.min(height - 1, y + vision)
        );
    }

    private double distanceTo(Cell from, Cell to) {
        return Math.pow(from.x() - to.x(), 2) + Math.pow(from.y() - to.y(), 2);
    }

    private Optional<Entity> findEntitiesCreatureCanEat(Cell cell, Creature creature) {
        Set<Entity> entities = map.get(cell);
        if (entities == null) return Optional.empty();

        return entities.stream()
                .filter(creature::canEat)
                .findFirst();
    }

    public Optional<Entity> getClosestEntity(Creature creature) {
        Cell creatureCell = findCellOfEntity(creature);
        SearchBoundaries b = getSquareBoundaries(creatureCell, creature.getVision());

        Entity closest = null;
        double closestDistance = Double.MAX_VALUE;

        for (int i = b.minX(); i <= b.maxX(); i++) {
            for (int j = b.minY(); j <= b.maxY(); j++) {
                Cell current = new Cell(i, j);
                if (current.equals(creatureCell)) continue;

                Optional<Entity> candidate = findEntitiesCreatureCanEat(current, creature);
                double distance = distanceTo(current, creatureCell);

                if (candidate.isPresent() && distance < closestDistance) {
                    closestDistance = distance;
                    closest = candidate.get();
                }
            }
        }

        return Optional.ofNullable(closest);
    }
}
