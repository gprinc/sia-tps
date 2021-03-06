import java.util.Objects;

public class Item {
    private double Strength;
    private double Agility;
    private double Expertise;
    private double Endurance;
    private double Life;
    private double id;

    public Item(double strength, double agility, double expertise, double endurance, double life, double id) {
        this.Strength = strength;
        this.Agility = agility;
        this.Expertise = expertise;
        this.Endurance = endurance;
        this.Life = life;
        this.id = id;
    }

    public double getStrength() {
        return Strength;
    }

    public double getAgility() {
        return Agility;
    }

    public double getExpertise() {
        return Expertise;
    }

    public double getEndurance() {
        return Endurance;
    }

    public double getLife() {
        return Life;
    }

    public double getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Double.compare(item.id, id) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
