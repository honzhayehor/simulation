package units.concrete;

import enviroment.WorldMap;
import units.abstraction.Entity;
import units.configs.BaseHp;

public class Rock extends Entity {
    protected Rock(BaseHp baseHp, WorldMap worldMap) {
        super(baseHp, worldMap);
    }

    @Override
    public boolean isPassable() {
        return false;
    }

    @Override
    public String getRepresentation() {
        return "🪨";
    }
}
