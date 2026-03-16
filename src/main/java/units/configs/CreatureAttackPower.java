package units.configs;

public enum CreatureAttackPower {
    ZEBRA(1),
    WOLF(50);

    private final int attackPower;

    CreatureAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    public int getAttackPower() {
        return attackPower;
    }
}
