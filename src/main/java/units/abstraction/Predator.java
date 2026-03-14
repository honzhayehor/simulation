package units.abstraction;

import enviroment.WorldMap;
import logic.AStar;
import units.configs.SightRange;

public abstract class Predator extends Creature{

    protected Predator(WorldMap map, AStar algorithm, SightRange range, int baseHp, int moveSpeed) {
        super(map, algorithm, range, baseHp, moveSpeed);
    }
}
