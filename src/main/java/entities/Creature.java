package entities;

import logic.*;

import java.util.List;
import java.util.Optional;

public abstract class Creature extends Entity{
    protected int hp;
    protected List<Coordinates> path;
    protected Species creatureData;
    protected PathfindingService pathfindingService;
    protected Sentry sentry;
    private static final float HUNGER_THRESHOLD = 0.75f;

    protected Creature(Coordinates coordinates, boolean walkable, Species species, PathfindingService pathfindingService) {
        super(coordinates, walkable);
        this.hp = species.hp();
        this. pathfindingService = pathfindingService;
    }

    protected void starve() {
        hp -= creatureData.hungerLossPerTurn();
        if (hp <= 0) {
            die();
        }
    }

    protected void offerNextMove() {
        if (isHungry()) {
            scanCurrentPosition().ifPresent(this::moveTowardsTarget);
        } else {
            // додати логіку wander();
        }
    }

    protected Optional<Coordinates> scanCurrentPosition() {
        throw new UnsupportedOperationException("scanCurrentPosition not implemented yet");
    }

    protected void moveTowardsTarget(Coordinates target) {
        List<Coordinates> path = pathfindingService.findPath(getCoordinates(), target);

        if (path.isEmpty()) return;
        if (path.size() == 1) return;

        moveTo(path.get(1));
    }

    protected void wander(Coordinates coordinates) {
        // logic
        moveTo(coordinates);
    }

    protected boolean isHungry() {
        return hp <= creatureData.hp() * HUNGER_THRESHOLD;
    }

    protected void moveTo(Coordinates coordinates) {
        sentry.requestMoveTo(coordinates);
    }

    protected void die() {
        sentry.requestCreatureDeath();
    }
}
