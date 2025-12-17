package logic;

import entities.Entity;
import entities.Grass;
import entities.Herbivore;

import java.util.Objects;
import java.util.function.Predicate;

public enum Species {
    ZEBRA(100, 2, 13, 3, 0.75f,  e -> e instanceof Grass),
    TIGER(75, 3, 7, 4, 0.6f, e -> e instanceof Herbivore);

    private final int hp;
    private final int speed;
    private final int hungerLossPerTurn;
    private final int vision;
    private final float hunger_threshold;
    private final Predicate<Entity> foodRule;

    Species(int hp, int speed, int hungerLossPerTurn, int vision, float hunger_threshold, Predicate<Entity> foodRule) {
        if (hp <= 0) throw new IllegalArgumentException("hp must be > 0");
        if (speed <= 0) throw new IllegalArgumentException("speed must be > 0");
        if (hungerLossPerTurn < 0) throw new IllegalArgumentException("hungerLossPerTurn must be >= 0");
        if (vision < 0) throw new IllegalArgumentException("vision must be >= 0");
        if (hunger_threshold < 0) throw new IllegalArgumentException("hunger threshold must be >= 0");
        this.hp = hp;
        this.speed = speed;
        this.hungerLossPerTurn = hungerLossPerTurn;
        this.vision = vision;
        this.hunger_threshold = hunger_threshold;
        this.foodRule = Objects.requireNonNull(foodRule);
    }

    public int hp() { return hp; }
    public int speed() { return speed; }
    public int hungerLossPerTurn() { return hungerLossPerTurn; }
    public int vision() { return vision; }
    public float hunger_threshold() { return hunger_threshold; }

    public boolean canEat(Entity e) {
        return e != null && foodRule.test(e);
    }
}
