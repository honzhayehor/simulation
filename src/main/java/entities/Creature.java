package entities;

import logic.*;

import java.util.List;
import java.util.Objects;

public abstract class Creature extends Entity {
    protected int hp;
    protected List<Cell> path;
    protected Species creatureData;
    protected PathfindingService pathfindingService;
    protected Actions actions;
    protected Cell currentTarget;

    protected Creature(Cell cell, Species species, PathfindingService pathfindingService, Actions actions) {
        super(cell, false);
        this.hp = species.hp();
        this.creatureData = Objects.requireNonNull(species);
        this.pathfindingService = Objects.requireNonNull(pathfindingService);
        this.actions = Objects.requireNonNull(actions);
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
                    Entity entity = actions.getEntityFromCell(cell);
                    if (testIfEntityIsFood(entity)) {
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
        if (path == null || path.isEmpty()) {
            path = pathfindingService.findPath(getCell(), target);
        }
        if (path.isEmpty()) return;
        if (path.size() == 1) return;

        Cell next = path.get(1);
        moveTo(next);
        path.removeFirst();
    }

    protected Cell wander() {
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

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Creature creature)) return false;
        if (!super.equals(object)) return false;
        return hp == creature.hp && creatureData == creature.creatureData;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), hp, creatureData);
    }
}
