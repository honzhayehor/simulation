package units.configs;

public enum Energy {
    GRASS(10);
    private int energy;
    Energy(int points) {
        this.energy = points;
    }

    public int getEnergy() { return energy;}
}
