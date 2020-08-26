public class Archer extends Player {

    public Archer(double height, Chest chest, Gloves gloves, Helmet helmet, Weapon weapon, Boots boots) {
        super(height, chest, gloves, helmet, weapon, boots);
    }

    public double performance () {
        return 0.9 * this.Atack() + 0.1 * this.Defense();
    }
}
