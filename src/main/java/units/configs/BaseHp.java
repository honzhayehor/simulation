package units.configs;

public enum BaseHp {
    ZEBRA(100),
    WOLF(100),
    GRASS(1),
    ROCK(1),
    TREE(1);

    private final int hp;

    BaseHp(int hp) {
        this.hp = hp;
    }

    public int getHp() {
        return hp;
    }
}
