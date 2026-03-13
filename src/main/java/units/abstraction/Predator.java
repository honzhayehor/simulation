package units.abstraction;

import enviroment.WorldMap;
import logic.Pathfinder;
import units.configs.SightRange;

public abstract class Predator extends Creature{

    protected Predator(WorldMap map, Pathfinder algorithm, SightRange range, int baseHp, int moveSpeed) {
        super(map, algorithm, range, baseHp, moveSpeed);
    }
}
