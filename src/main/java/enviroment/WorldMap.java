package enviroment;

import units.Entity;

import java.util.HashMap;
import java.util.Map;

public final class WorldMap {
    private final Map<Entity, double[]> map;
    public WorldMap() {
        map = new HashMap<>();
    }
}
