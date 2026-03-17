package units.abstraction;

import enviroment.WorldMap;
import logic.concrete.AStar;
import units.configs.*;
import units.interfaces.MeatBased;

public abstract class Herbivore extends Creature implements MeatBased {
    protected Herbivore(WorldMap map, AStar algorithm, SightRange range, BaseHp baseHp, CreatureMoveSpeed creatureMoveSpeed, CreatureAttackPower attackPower, StarvationRate starvationRate) {
        super(map, algorithm, range, baseHp, creatureMoveSpeed, attackPower, starvationRate);
    }
}
