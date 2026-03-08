package units.abstraction;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Entity {
    protected final int id;
    protected static AtomicInteger globalIdCounter = new AtomicInteger(0);

    protected Entity() {
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
}
