package entities;

import attributes.Edible;
import data.Species;
import logic.*;

import java.util.Deque;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Creature extends Entity {
    private int hp;
    private Deque<Cell> path;
    private Species creatureData;
    private PathfindingService pathfindingService;
    private Actions actions;
    private Cell currentTarget;

    protected Creature(Cell cell, Species species, PathfindingService pathfindingService, Actions actions) {
        super(cell, false);
        this.creatureData = Objects.requireNonNull(species);
        this.hp = creatureData.hp();
        this.pathfindingService = Objects.requireNonNull(pathfindingService);
        this.actions = Objects.requireNonNull(actions);
    }

    public void makeMove() {
        if (hp <= 0) return;
        starve();
        if (hp <= 0) return;

        Cell target = (currentTarget != null) ? currentTarget : offerTarget();

        if (target == null) {
            wander();
            return;
        }

        if (!target.equals(currentTarget)) {
            currentTarget = target;
            path = null;
        }

        if (isInVicinity(target)) {
            Entity targetToEat = actions.getEntityFromCell(target);
            if (targetToEat != null) {
                eat(targetToEat);
                actions.requestEntityDeath(targetToEat);
            }
            currentTarget = null;
            path = null;
        } else {
            moveTowardsTarget(target);
        }
    }

    protected void eat(Entity entity) {
        if (entity instanceof Edible ed && creatureData.canEat(entity)) {
            hp += ed.energy();
        }
    }

    protected void starve() {
        hp -= creatureData.hungerLossPerTurn();
        if (hp <= 0) {
            die();
        }
    }

    protected boolean isInVicinity(Cell targetCell) {
        Cell current = getCell();

        int dx = Math.abs(current.x() - targetCell.x());
        int dy = Math.abs(current.y() - targetCell.y());

        return dx + dy == 1;
    }

    protected Cell offerTarget() {
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
        return null;
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

    protected void wander() {
        List<Cell> candidates = actions.getAvailableCellsForNextMove(getCell(), 1);

        candidates.removeIf(c -> !isInVicinity(c));

        candidates.removeIf(c -> {
            Entity e = actions.getEntityFromCell(c);
            return e != null && !e.isWalkable();
        });

        if (candidates.isEmpty()) return;

        int index = ThreadLocalRandom.current().nextInt(candidates.size());
        moveTo(candidates.get(index));
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
