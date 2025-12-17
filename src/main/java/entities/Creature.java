package entities;

import logic.*;

import java.util.List;
import java.util.Objects;

public abstract class Creature extends Entity {
    protected int hp;
    protected List<Cell> path;
    protected Species creatureData;
    protected PathfindingService pathfindingService;
    protected Sentry sentry;
    protected Cell currentTarget;

    protected Creature(Cell cell, Species species, PathfindingService pathfindingService, Sentry sentry) {
        super(cell, false);
        this.hp = species.hp();
        this.creatureData = Objects.requireNonNull(species);
        this.pathfindingService = Objects.requireNonNull(pathfindingService);
        this.sentry = Objects.requireNonNull(sentry);
    }

    public void makeMove() {
        starve();
        if (hp <= 0) return;

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
                    Entity entity = cell.getEntity();
                    if (testIfEntityIsFood(entity)) {
                        return entity.getCell();
                    }
                }
            }
        }
        return wander();
    }

    protected List<Cell> scanCurrentPosition(int vision) {
        return sentry.getAvailableCellsForNextMove(getCell(), vision); // Implement that method in SENTRY!!
    }

    protected void moveTowardsTarget(Cell target) {
        if (path == null || path.isEmpty()) {
            path = pathfindingService.findPath(getCell(), target);
        }
        if (path.isEmpty()) return;
        if (path.size() == 1) return;

        Cell next = path.get(1);
        moveTo(next);
        path.remove(0);
    }

    protected Cell wander() {
        //Create a wander method; !!
        throw new RuntimeException("This method is not yet implemented");
    }

    protected boolean isHungry() {
        return hp <= creatureData.hp() * creatureData.hunger_threshold();
    }

    protected void moveTo(Cell cell) {
        sentry.requestMoveTo(cell, this); // IMPLEMENT THAT IN SENTRY!!
    }

    protected void die() {
        sentry.requestCreatureDeath(this); // IMPLEMENT
    }

    protected boolean testIfEntityIsFood(Entity entity) {
        return entity != null && creatureData.canEat(entity);
    }
}
