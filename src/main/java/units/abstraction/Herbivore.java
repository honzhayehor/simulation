package units.abstraction;

import enviroment.WorldMap;
import logic.AStar;
import units.configs.SightRange;
import units.interfaces.MeatBased;

public abstract class Herbivore extends Creature implements MeatBased {
    protected Herbivore(WorldMap map, AStar algorithm, SightRange range, int baseHp, int moveSpeed) {
        super(map, algorithm, range, baseHp, moveSpeed);
    }
}
