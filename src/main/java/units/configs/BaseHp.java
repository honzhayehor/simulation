package units.configs;

public enum BaseHp {
    ZEBRA(100);

    private final int hp;

    BaseHp(int hp) {
        this.hp = hp;
    }

    public int getHp() {
        return hp;
    }
}
