package units.concrete;

import enviroment.WorldMap;
import logic.interfaces.Pathfinder;
import units.abstraction.Creature;
import units.configs.*;
import units.interfaces.MeatBased;

public class Zebra extends Creature implements MeatBased {
    protected Zebra(
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

    public static Zebra create(WorldMap map, Pathfinder pathfinder) {
        return new Zebra(
                map,
                pathfinder,
                SightRange.ZEBRA,
                BaseHp.ZEBRA,
                CreatureMoveSpeed.ZEBRA,
                CreatureAttackPower.ZEBRA,
                StarvationRate.ZEBRA
        );
    }

    @Override
    protected FoodChain getFoodChain() {
        return FoodChain.HERBIVORE;
    }

    @Override
    public String getRepresentation() {
        return "🦓";
    }

    @Override
    public boolean isPassable() {
        return false;
    }

    @Override
    public int getEnergy() {
        return Energy.ZEBRA.getEnergy();
    }
}
