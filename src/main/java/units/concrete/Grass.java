package units.concrete;

import enviroment.WorldMap;
import units.abstraction.Entity;
import units.configs.BaseHp;
import units.configs.Energy;
import units.interfaces.PlantBased;

public class Grass extends Entity implements PlantBased {
    protected Grass(WorldMap worldMap, BaseHp baseHp) {
        super(baseHp, worldMap);
    }

    public Grass create(WorldMap map) {
        return new Grass(
                map,
                BaseHp.GRASS
        );
    }

    @Override
    public String getRepresentation() {
        return "🌿";
    }
    @Override
    public int getEnergy() {
        return Energy.GRASS.getEnergy();
    }

    @Override
    public boolean isPassable() {
        return true;
    }
}
