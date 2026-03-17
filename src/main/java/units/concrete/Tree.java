package units.concrete;

import enviroment.WorldMap;
import units.abstraction.Entity;
import units.configs.BaseHp;

public class Tree extends Entity {

    protected Tree(BaseHp baseHp, WorldMap worldMap) {
        super(baseHp, worldMap);
    }

    public static Tree create(WorldMap worldMap) {
        return new Tree(BaseHp.TREE, worldMap);
    }

    @Override
    public String getRepresentation() {
        return "🌳";
    }

    @Override
    public boolean isPassable() {
        return false;
    }
}
