package units.abstraction;

import enviroment.WorldMap;
import logic.AStar;
import units.configs.*;

public abstract class Predator extends Creature{
    protected Predator(WorldMap map, AStar algorithm, SightRange range, BaseHp baseHp, CreatureMoveSpeed creatureMoveSpeed, CreatureAttackPower attackPower, StarvationRate starvationRate) {
        super(map, algorithm, range, baseHp, creatureMoveSpeed, attackPower, starvationRate);
    }


}
