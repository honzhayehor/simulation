package entities;

import data.Species;
import logic.*;

import java.util.Deque;
import java.util.List;
import java.util.Objects;

public abstract class Creature extends Entity {
    private int hp;
    private Deque<Cell> path;
    private Species creatureData;
    private PathfindingService pathfindingService;
    private Actions actions;
    private Cell currentTarget;

    protected Creature(Cell cell, Species species, PathfindingService pathfindingService, Actions actions) {
        super(cell, false);
        this.hp = species.hp();
        this.creatureData = Objects.requireNonNull(species);
        this.pathfindingService = Objects.requireNonNull(pathfindingService);
        this.actions = Objects.requireNonNull(actions);
    }

    public void makeMove() {
        if (hp <= 0) return;
        starve();

        Cell target = offerNextMove();
        if (target == null) return;

        if (currentTarget == null || !currentTarget.equals(target)) {
            currentTarget = target;
            path = null;
        }
        moveTowardsTarget(target);
    }

    protected void starve() {
        hp -= creatureData.hungerLossPerTurn();
        if (hp <= 0) {
            die();
        }
    }

    protected Cell offerNextMove() {
        if (isHungry()) {
            List<Cell> possibleMoves = scanCurrentPosition(creatureData.vision());
            if (!possibleMoves.isEmpty()) {
                for (Cell cell : possibleMoves) {
                    Entity entity = actions.getEntityFromCell(cell);
                    if (entity != null && testIfEntityIsFood(entity)) {
                        return entity.getCell();
                    }
                }
            }
        }
        return wander();
    }

    protected List<Cell> scanCurrentPosition(int vision) {
        return actions.getAvailableCellsForNextMove(getCell(), vision); // Implement that method in SENTRY!!
    }

    protected void moveTowardsTarget(Cell target) {
        if (target == null) {
            path = null;
            return;
        }

        if (path == null || path.isEmpty()) {
            path = pathfindingService.findPath(getCell(), target);

            if (path == null || path.size() <= 1) {
                path = null;
                return;
            }
            path.pollFirst();
        }
        Cell next = path.pollFirst();
        if (next != null) {
            moveTo(next);
        }
        if (path.isEmpty()) {
            path = null;
        }
    }

    protected Cell wander() {
        // TODO: Implement this method!
        throw new RuntimeException("This method is not yet implemented");
    }

    protected boolean isHungry() {
        return hp <= creatureData.hp() * creatureData.hunger_threshold();
    }

    protected void moveTo(Cell cell) {
        actions.requestMoveToCell(cell, this);
    }

    protected void die() {
        actions.requestEntityDeath(this);
    }

    protected boolean testIfEntityIsFood(Entity entity) {
        return creatureData.canEat(entity);
    }

}
