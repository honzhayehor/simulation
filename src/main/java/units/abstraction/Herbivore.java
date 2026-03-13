package units.abstraction;

import enviroment.WorldMap;
import logic.Pathfinder;
import units.configs.SightRange;
import units.interfaces.MeatBased;

public abstract class Herbivore extends Creature implements MeatBased {
    protected Herbivore(WorldMap map, Pathfinder algorithm, SightRange range, int baseHp, int moveSpeed) {
        super(map, algorithm, range, baseHp, moveSpeed);
    }
}
