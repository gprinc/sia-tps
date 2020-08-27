public class Infiltrate extends Player {

    public Infiltrate(double height, Chest chest, Gloves gloves, Helmet helmet, Weapon weapon, Boots boots) {
        super(height, chest, gloves, helmet, weapon, boots);
    }
    @Override
    public double performance () {
        return 0.8 * this.Atack() + 0.3 * this.Defense();
    }
}
