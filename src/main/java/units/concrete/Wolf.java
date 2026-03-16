package units.concrete;

import enviroment.WorldMap;
import logic.Pathfinder;
import units.abstraction.Creature;
import units.configs.*;

import java.security.AlgorithmConstraints;

public class Wolf extends Creature {
    protected Wolf(
            WorldMap map,
            Pathfinder algorithm,
            SightRange range,
            BaseHp baseHp,
            CreatureMoveSpeed creatureMoveSpeed,
            CreatureAttackPower attackPower,
            StarvationRate starvationRate
    ) {
        super(map, algorithm, range, baseHp, creatureMoveSpeed, attackPower, starvationRate);
    }

    public static Wolf create(WorldMap worldMap, Pathfinder pathfinder) {
        return new Wolf(
                worldMap,
                pathfinder,
                SightRange.WOLF,
                BaseHp.WOLF,
                CreatureMoveSpeed.WOLF,
                CreatureAttackPower.WOLF,
                StarvationRate.WOLF
        );
    }

    @Override
    protected FoodChain getFoodChain() {
        return FoodChain.PREDATOR;
    }

    @Override
    public String getRepresentation() {
        return "🐺";
    }

    @Override
    public boolean isPassable() {
        return false;
    }
}
