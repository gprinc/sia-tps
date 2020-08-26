public class Warrior extends Player {

    public Warrior(double height, Chest chest, Gloves gloves, Helmet helmet, Weapon weapon, Boots boots) {
        super(height, chest, gloves, helmet, weapon, boots);
    }

    public double performance () {
        return 0.6 * this.Atack() + 0.6 * this.Defense();
    }
}
