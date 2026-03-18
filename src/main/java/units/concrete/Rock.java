package units.concrete;

import enviroment.WorldMap;
import units.abstraction.Entity;
import units.configs.BaseHp;

public class Rock extends Entity {
    protected Rock(BaseHp baseHp, WorldMap worldMap) {
        super(baseHp, worldMap);
    }

    public static Rock create(WorldMap worldMap) {
        return new Rock(BaseHp.ROCK, worldMap);
    }

    @Override
    public boolean isPassable() {
        return false;
    }

    @Override
    public void makeMove() {
        // Method is not implemented, because this entity should not be moved
    }

    @Override
    public String getRepresentation() {
        return "🪨";
    }
}
