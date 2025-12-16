package logic;

public enum Species {
    ZEBRA(100, 2),
    TIGER(75, 3);

    private final int hp;
    private final int speed;

    Species(int hp, int speed) {
        this.hp = hp;
        this.speed = speed;
    }

    public int hp() { return hp; }
    public int speed() { return speed; }
}
