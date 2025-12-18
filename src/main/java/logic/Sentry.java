package logic;

import entities.Creature;

import java.util.List;

public class Sentry {
    public int requestMoveTo(Cell cell, Creature creature) {
        return -1;
    }

    public void requestCreatureDeath(Creature creature) {
        Cell currentCell = creature.getCell();
    }

    public List<Cell> getAvailableCellsForNextMove(Cell currentCreaturePosition, int vision) {
        return List.of(new Cell(1,1));
    }
}
