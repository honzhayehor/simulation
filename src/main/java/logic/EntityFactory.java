package logic;

import entities.*;
import java.util.concurrent.ThreadLocalRandom;

public class EntityFactory {
    public static Entity getEntity(Cell cell) {
        int chance = ThreadLocalRandom.current().nextInt(100);
        if (chance < 65) return null;

        return switch (ThreadLocalRandom.current().nextInt(3)) {
            case 0 -> new Grass(cell);
            case 1 -> new Tree(cell);
            case 2 -> new Rock(cell);
            default -> null;
        };
    }
}