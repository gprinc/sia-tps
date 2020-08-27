public class Item {
    private int Strength;
    private int Agility;
    private int Expertise;
    private int Endurance;
    private int Life;
    private int id;

    public Item(int strength, int agility, int expertise, int endurance, int life, int id) {
        this.Strength = strength;
        this.Agility = agility;
        this.Expertise = expertise;
        this.Endurance = endurance;
        this.Life = life;
        this.id = id;
    }

    public int getStrength() {
        return Strength;
    }

    public int getAgility() {
        return Agility;
    }

    public int getExpertise() {
        return Expertise;
    }

    public int getEndurance() {
        return Endurance;
    }

    public int getLife() {
        return Life;
    }

    public int getId() {
        return id;
    }
}
