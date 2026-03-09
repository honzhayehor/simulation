package units.abstraction;

import units.configs.FoodChain;
import units.interfaces.Edible;

public abstract class Creature extends Entity {
    protected int hp;
    protected int maxHp;
    protected boolean isAlive = true;
    protected int moveSpeed;

    protected abstract void makeMove();
    protected abstract FoodChain getFoodChain();
    protected void die() { isAlive = false;}

    protected void reduceHp(int amount) {
        hp = Math.max(0, hp - amount);
        if (hp == 0) die();
    }
    protected boolean canEat(Entity entity) {
        return getFoodChain().canEat(entity);
    }

    protected void eat(Edible edible) {
        hp = Math.min(maxHp, hp + edible.getEnergy());
    }
}
