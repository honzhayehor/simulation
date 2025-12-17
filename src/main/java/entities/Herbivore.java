package entities;

import logic.Cell;
import logic.PathfindingService;
import logic.Species;

public class Herbivore extends Creature {
    protected Herbivore(Cell cell, boolean walkable, Species species, PathfindingService pathfindingService) {
        super(cell, walkable, species, pathfindingService);
    }

    @Override
    public String getAvatar() {
        return "";
    }
}
