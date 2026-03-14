package units.abstraction;

import enviroment.WorldMap;
import logic.AStar;
import units.configs.BaseHp;
import units.configs.CreatureAttackPower;
import units.configs.CreatureMoveSpeed;
import units.configs.SightRange;

public abstract class Predator extends Creature{
    protected Predator(WorldMap map, AStar algorithm, SightRange range, BaseHp baseHp, CreatureMoveSpeed creatureMoveSpeed, CreatureAttackPower attackPower) {
        super(map, algorithm, range, baseHp, creatureMoveSpeed, attackPower);
    }


}
