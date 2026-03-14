package units.abstraction;

import enviroment.WorldMap;
import logic.AStar;
import units.configs.BaseHp;
import units.configs.CreatureAttackPower;
import units.configs.CreatureMoveSpeed;
import units.configs.SightRange;
import units.interfaces.MeatBased;

public abstract class Herbivore extends Creature implements MeatBased {
    protected Herbivore(WorldMap map, AStar algorithm, SightRange range, BaseHp baseHp, CreatureMoveSpeed creatureMoveSpeed, CreatureAttackPower attackPower) {
        super(map, algorithm, range, baseHp, creatureMoveSpeed, attackPower);
    }
}
