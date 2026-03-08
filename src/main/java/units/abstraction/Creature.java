package units.abstraction;

public abstract class Creature extends Entity {
    protected int hp;
    protected int moveSpeed;

    public abstract void makeMove();
}
