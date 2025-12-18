package entities;

import logic.Cell;
import logic.PathfindingService;
import logic.Sentry;
import logic.Species;

public class Herbivore extends Creature {
    protected Herbivore(Cell cell,  Species species, PathfindingService pathfindingService, Sentry sentry) {
        super(cell, species, pathfindingService, sentry);
    }

    @Override
    public String getAvatar() {
        return "";
    }
}
