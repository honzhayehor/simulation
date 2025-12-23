package entities;

import logic.Cell;
import logic.PathfindingService;
import logic.Actions;
import logic.Species;

public class Herbivore extends Creature {
    protected Herbivore(Cell cell,  Species species, PathfindingService pathfindingService, Actions actions) {
        super(cell, species, pathfindingService, actions);
    }
}
