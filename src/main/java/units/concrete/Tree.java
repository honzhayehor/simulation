package units.concrete;

import units.abstraction.Entity;

public class Tree extends Entity {

    @Override
    public String getRepresentation() {
        return "🌳";
    }

    @Override
    public boolean isPassable() {
        return false;
    }
}
