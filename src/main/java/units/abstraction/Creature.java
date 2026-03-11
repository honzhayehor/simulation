package units.abstraction;

import enviroment.Cell;
import enviroment.WorldMap;
import logic.Pathfinder;
import units.configs.FoodChain;
import units.configs.SightRange;
import units.interfaces.Edible;

import java.util.List;

public abstract class Creature extends Entity {
    protected int hp;
    protected int maxHp;
    protected boolean isAlive = true;
    protected int moveSpeed;
    protected final WorldMap map;
    protected final Pathfinder algorithm;
    protected final int vision;

    protected Creature(WorldMap map, Pathfinder algorithm, SightRange range) {
        this.map = map;
        this.algorithm = algorithm;
        this.vision = range.getVisionRange();
    }

    public abstract void makeMove();
    protected abstract FoodChain getFoodChain();
    protected void die() { isAlive = false;}

    protected void reduceHp(int amount) {
        hp = Math.max(0, hp - amount);
        if (hp == 0) die();
    }
    public boolean canEat(Entity entity) {
        return getFoodChain().canEat(entity);
    }

    protected void eat(Edible edible) {
        hp = Math.min(maxHp, hp + edible.getEnergy());
    }

    protected List<Cell> findPathToDestination(Entity entity) {
        Cell cell = map.findCellOfEntity(entity);
        return algorithm.findPath(cell);
    }

    protected boolean moveToDestination(Cell cell) {
        return map.suggestMove(cell);
    }

    public int getVision() {return vision;}

    protected Entity lookForFoodInVicinity() {
        return map.getClosestEntity(this).orElse(null);
    }
}
