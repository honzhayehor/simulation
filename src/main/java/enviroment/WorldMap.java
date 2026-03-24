package enviroment;

import units.abstraction.Creature;
import units.abstraction.Entity;

import java.util.*;

public final class WorldMap {
    private final Map<Cell, Set<Entity>> map;
    private final Map<Entity, Cell> entityLocation = new HashMap<>();
    private final Map<String, Cell> cells = new HashMap<>();
    private int width;
    private int height;

    public WorldMap(int width, int height) {
        map = initMap(width, height);
    }

    public WorldMap() {
        map = initMap(10, 10);
    }

    public Map<Cell, Set<Entity>> getMap() {
        return Collections.unmodifiableMap(map);
    }


    private Map<Cell, Set<Entity>> initMap(int xLength, int yLength) {
        Map<Cell, Set<Entity>> wm = new HashMap<>();
        width = xLength;
        height = yLength;
        if (xLength < 0 || yLength < 0) {
            throw new IllegalArgumentException("x or y cannot be null");
        }
        for (int i = 0; i < xLength; i++) {
            for (int j = 0; j < yLength; j++) {
                wm.put(registerCell(i, j), new HashSet<>());
            }
        }
        return wm;
    }

    public List<Entity> getAllEntities() {
        return map.values().stream().flatMap(Set::stream).toList();
    }

    private Cell registerCell(int x, int y) {
        Cell cell = new Cell(x, y);
        cells.put(x + "," + y, cell);
        return cell;
    }

    public Optional<Cell> cellOf(int x, int y) {
        return Optional.ofNullable(cells.get(x + "," + y));
    }

    public boolean cellContainsEntity(Cell cell) {
        if (cell == null) throw new IllegalArgumentException("Cell cannot be null");
        Set<Entity> list = map.get(cell);
        return !list.isEmpty();
    }

    public boolean cellContainsEntity(int coordinateX, int coordinateY) {
        if (coordinateX < 0 || coordinateY < 0)
            throw new IllegalArgumentException("Coordinates could not be negative");

        Cell cell = cellOf(coordinateX, coordinateY)
                .orElseThrow(() -> new IllegalArgumentException("Cell not found"));

        return cellContainsEntity(cell);
    }

    public void addEntityToCell(Cell cell, Entity entity) {
        if (cell == null || entity == null) {
            throw new IllegalArgumentException("Cell and entity cannot be null");
        }
        Set<Entity> entities = map.get(cell);
        entities.add(entity);
        entityLocation.put(entity, cell);
    }

    public boolean suggestMove(Cell cell) {
        Set<Entity> entities = map.get(cell);
        if (entities == null) throw new IllegalArgumentException("Cell not found: " + cell);
        if (entities.isEmpty()) {
            return true;
        }
        return entities.stream().allMatch(Entity::isPassable);
    }

    public void moveEntity(Entity entity, Cell destination) {
        if (!suggestMove(destination)) return;
        Cell current = findCellOfEntity(entity);
        map.get(current).remove(entity);
        map.get(destination).add(entity);
        entityLocation.put(entity, destination);
    }

    public int getWidth() {return width;}

    public int getHeight() {return height;}

    public Cell findCellOfEntity(Entity entity) {
        return entityLocation.get(entity);
    }

    public void removeEntity(Entity entity, Cell cell) {
        Objects.requireNonNull(entity, "Entity cannot be null");
        Objects.requireNonNull(cell, "Cell cannot be null");

        Set<Entity> entities = map.get(cell);

        boolean removed = entities.remove(entity);
        if (!removed) throw new IllegalArgumentException("Entity not found in cell: " + cell);
        entityLocation.remove(entity);
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
        return Math.pow((double) from.x() - to.x(), 2) + Math.pow((double) from.y() - to.y(), 2);
    }

    private Optional<Entity> findEntitiesCreatureCanEat(Cell cell, Creature creature) {
        Set<Entity> entities = map.get(cell);
        if (entities == null) return Optional.empty();

        return entities.stream()
                .filter(creature::canEat)
                .findFirst();
    }

    public boolean isValidCell(Cell cell) {
        return cell.x() >= 0 && cell.x() < width
                && cell.y() >= 0 && cell.y() < height;
    }

    public Optional<Entity> getClosestEntity(Creature creature) {
        Cell creatureCell = findCellOfEntity(creature);
        if (creatureCell == null) { return Optional.empty(); }
        SearchBoundaries b = getSquareBoundaries(creatureCell, creature.getVision());

        Entity closest = null;
        double closestDistance = Double.MAX_VALUE;

        for (int i = b.minX(); i <= b.maxX(); i++) {
            for (int j = b.minY(); j <= b.maxY(); j++) {
                Cell current = cellOf(i, j).orElseThrow(() -> new IllegalArgumentException("Cell does not exist"));
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

    public List<Cell> asList() {
        return List.copyOf(map.keySet());
    }
}
