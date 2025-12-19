package logic;

import entities.Creature;
import entities.Entity;

import java.util.List;

public record Actions(WorldMap worldMap) {

    public void requestMoveToCell(Cell cell, Creature creature) {
        boolean isTargetCellWalkable = worldMap.getEntityInCell(cell).isWalkable();
        boolean isTargetCellIsSameAsInitialCell = !creature.getCell().equals(cell);

        if (isTargetCellWalkable && isTargetCellIsSameAsInitialCell) {
            worldMap.setEntityInCell(cell, creature);
        }
    }

    public void requestEntityDeath(Entity entity) {
        Cell creatureCurrentCell = entity.getCell();
        boolean isEntityInTargetCellIsCurrentEntity = worldMap.getEntityInCell(creatureCurrentCell).equals(entity);
        if (isEntityInTargetCellIsCurrentEntity) {
            worldMap.removeEntityFromCell(creatureCurrentCell, entity);
        }
    }

    public List<Cell> getAvailableCellsForNextMove(Cell currentCreaturePosition, int vision) {
        // TODO: Implement this method
        return List.of(new Cell(1, 1));
    }

    public Entity getEntityFromCell(Cell cell) {

        // TODO: Implement this method
        return null;
    }
}
