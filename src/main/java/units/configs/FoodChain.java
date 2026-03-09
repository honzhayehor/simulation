package units.configs;

import units.abstraction.Entity;
import units.interfaces.MeatBased;
import units.interfaces.PlantBased;

public enum FoodChain {
    HERBIVORE(PlantBased.class),
    PREDATOR(MeatBased.class);

    private final Class<?> foodType;

    FoodChain(Class<?> foodType) {
        this.foodType = foodType;
    }

    public boolean canEat(Entity entity) {
        return foodType.isInstance(entity);
    }
}
