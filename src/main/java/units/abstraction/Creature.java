package units.abstraction;

import enviroment.Cell;
import enviroment.WorldMap;
import logic.interfaces.Pathfinder;
import units.configs.*;
import units.interfaces.Edible;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@SuppressWarnings("java:S2160")
public abstract class Creature extends Entity {
    private final int moveSpeed;
    protected final Pathfinder algorithm;
    private static final int[][] DIRECTIONS = {{0,-1}, {0,1}, {-1,0}, {1,0}};
    private static final Random RANDOM = new Random();
    protected final int vision;
    protected final int attackPower;
    protected final int starvationRate;

    protected Creature(WorldMap map, Pathfinder algorithm, SightRange range, BaseHp baseHp, CreatureMoveSpeed creatureMoveSpeed, CreatureAttackPower attackPower, StarvationRate starvationRate) {
        super(baseHp, map);
        this.algorithm = algorithm;
        this.vision = range.getVisionRange();
        this.moveSpeed = creatureMoveSpeed.getSpeed();
        this.attackPower = attackPower.getAttackPower();
        this.starvationRate = starvationRate.getRate();
    }

    @Override
    public void makeMove() {
        if (!isAlive()) {
            log.info("Death of entity: class={}, id={}, hp={}",
                    getClass().getSimpleName(),
                    id,
                    hp);
            return;
        }
        reduceHp(starvationRate);
        log.info("Creature has been starving. Current hp: {}", hp);
        for (int i = 0; i < moveSpeed; i++) {
            performSingleStep();
        }
    }

    public void performSingleStep() {
        lookForFoodInVicinity().ifPresentOrElse(
                food -> {
                    List<Cell> path = findPathToDestination(food);
                    if (path.size() < 2) {
                        log.info("Creature will not move: path size is less than 2");
                        return;
                    }

                    Cell nextStep = path.get(1);
                    log.info("Creature's next step: x={}, y={}", nextStep.x(), nextStep.y());

                    if (isAdjacentToFood(path, food)) {
                        if (attack(attackPower, food)) {
                            eat((Edible) food);
                            log.info("Creature ate the food");
                        }
                    } else {
                        moveToDestination(nextStep);
                        log.info("Creature has moved to destination");
                    }
                },
                this::wander
        );
    }

    private void wander() {
        log.info("Creature is wandering");
        Cell current = map.findCellOfEntity(this);

        List<Cell> passableNeighbors = Arrays.stream(DIRECTIONS)
                .map(dir -> map.cellOf(current.x() + dir[0], current.y() + dir[1]))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(map::isValidCell)
                .filter(map::suggestMove)
                .toList();

        if (passableNeighbors.isEmpty()) return;

        Cell randomCell = passableNeighbors.get(RANDOM.nextInt(passableNeighbors.size()));
        moveToDestination(randomCell);
    }

    protected abstract FoodChain getFoodChain();

    protected boolean attack(int attackPower, Entity entity){
        return entity.reduceHp(attackPower);
    }

    private boolean isAdjacentToFood(List<Cell> path, Entity food) {
        return path.size() == 2 && food instanceof Edible;
    }

    public boolean canEat(Entity entity) {
        return getFoodChain().canEat(entity);
    }

    private boolean isAlive() {
        return hp > 0;
    }

    protected void eat(Edible edible) {
        hp = Math.min(maxHp, hp + edible.getEnergy());
    }

    protected List<Cell> findPathToDestination(Entity entity) {
        Cell currentPost = map.findCellOfEntity(this);
        Cell target = map.findCellOfEntity(entity);
        return algorithm.findPath(currentPost, target);
    }

    protected void moveToDestination(Cell cell) {
        map.moveEntity(this, cell);
    }

    public int getVision() {return vision;}

    protected Optional<Entity> lookForFoodInVicinity() {
        return map.getClosestEntity(this);
    }
}
