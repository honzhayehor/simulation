package logic;

public enum Energy {
    GRASS(10),
    MEAT(20);

    private int energy;

    Energy(int energy) {
        this.energy = energy;
    }
    public int getEnergy() { return this.energy;}
}
