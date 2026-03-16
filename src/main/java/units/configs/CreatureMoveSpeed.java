package units.configs;

public enum CreatureMoveSpeed {
    ZEBRA(2),
    WOLF(2);

    private final int speed;
    CreatureMoveSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }
}
