package units.abstraction;

import enviroment.Cell;
import enviroment.WorldMap;
import units.configs.BaseHp;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Entity {
    protected final int id;
    protected final WorldMap map;
    protected int hp;
    protected final int maxHp;
    protected static AtomicInteger globalIdCounter = new AtomicInteger(0);

    protected Entity(BaseHp baseHp, WorldMap worldMap) {
        this.maxHp = baseHp.getHp();
        this.hp = baseHp.getHp();
        map = worldMap;
        id = globalIdCounter.incrementAndGet();
    }

    public abstract String getRepresentation();
    public abstract boolean isPassable();
    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Entity entity)) return false;
        return id == entity.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    protected boolean reduceHp(int amount) {
        hp = Math.max(0, hp - amount);
        if (hp == 0) {
            die();
            return true;
        }
        return false;
    }

    public abstract void makeMove();

    protected void die() {
        Cell current = map.findCellOfEntity(this);
        map.removeEntity(this, current);
    }
}
