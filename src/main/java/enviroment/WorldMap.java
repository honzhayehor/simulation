package enviroment;

import units.abstraction.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class WorldMap {
    private final Map<Cell, List<Entity>> map;
    public WorldMap(int width, int height) {
        map = initMap(width, height);
    }

    private Map<Cell, List<Entity>> initMap(int xLength, int yLength) {
        Map<Cell, List<Entity>> wm = new HashMap<>();
        if (xLength <= 0 || yLength <= 0) {
            throw new IllegalArgumentException("x or y cannot be null");
        }
        for (int i = 0; i < xLength; i++) {
            for (int j = 0; j < yLength; j++) {
                wm.put(new Cell(i, j), new ArrayList<>());
            }
        }
        return wm;
    }

    public boolean cellContainsEntity(Cell cell) {
        if (cell == null) throw new IllegalArgumentException("Cell cannot be null");
        List<Entity> list = map.get(cell);
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
        for (Cell cell : map.keySet()) {
            if (cell.x() == x && cell.y() == y) {
                return cell;
            }
        }
        return null;
    }

}
