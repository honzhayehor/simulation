package logic;

import entities.Creature;
import entities.Entity;

import java.util.ArrayList;
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

        boolean isEntityInTargetCellIsCurrentEntity = worldMap
                .getEntityInCell(creatureCurrentCell)
                .equals(entity);

        if (isEntityInTargetCellIsCurrentEntity) {
            worldMap.removeEntityFromCell(creatureCurrentCell, entity);
        }
    }

    public List<Cell> getAvailableCellsForNextMove(Cell currentCreaturePosition, int vision) {
        int x0 = currentCreaturePosition.x();
        int y0 = currentCreaturePosition.y();

        int minX = Math.max(0, x0 - vision);
        int maxX = Math.min(worldMap.getWidth()  - 1, x0 + vision);
        int minY = Math.max(0, y0 - vision);
        int maxY = Math.min(worldMap.getHeight() - 1, y0 + vision);

        List<Cell> result = new ArrayList<>();

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                if (x == x0 && y == y0) continue;

                int dist = Math.abs(x - x0) + Math.abs(y - y0);
                if (dist > vision) continue;

                Cell c = worldMap.getCell(x, y);
                if (worldMap.isFree(c)) {
                    result.add(c);
                }
            }
        }
        return result;
    }

    public Entity getEntityFromCell(Cell cell) {
        return worldMap.getEntityInCell(cell);
    }
}
