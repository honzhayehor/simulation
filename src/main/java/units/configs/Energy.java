package units.configs;

public enum Energy {
    GRASS(10);

    private final int points;
    Energy(int points) {
        this.points = points;
    }

    public int getEnergy() { return points;}
}
