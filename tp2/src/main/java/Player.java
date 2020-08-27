public class Player {
    private double height;
    private Chest chest;
    private Gloves gloves;
    private Helmet helmet;
    private Weapon weapon;
    private Boots boots;

    public Player(double height, Chest chest, Gloves gloves, Helmet helmet, Weapon weapon, Boots boots) {
        this.height = height;
        this.chest = chest;
        this.gloves = gloves;
        this.helmet = helmet;
        this.weapon = weapon;
        this.boots = boots;
    }

    public double ATM(){
        return 0.7 - Math.pow(((3 * height) - 5),4) + Math.pow(((3* height) - 5),2) + (height/4);
    }

    public double DEM(){
        return 1.9 + Math.pow(((2.5 * height) - 4.16),4) - Math.pow(((2.5 * height) - 4.16),2) + (3 * height / 10);
    }

    public double Strength () {
        double aux = chest.getStrength() + gloves.getStrength() + helmet.getStrength() + weapon.getStrength() + boots.getStrength();
        return 100 * Math.tanh(0.01 * aux);
    }

    public double Agility () {
        double aux = chest.getAgility() + gloves.getAgility() + helmet.getAgility() + weapon.getAgility() + boots.getAgility();
        return Math.tanh(0.01 * aux);
    }

    public double Expertise () {
        double aux = chest.getExpertise() + gloves.getExpertise() + helmet.getExpertise() + weapon.getExpertise() + boots.getExpertise();
        return 0.6 * Math.tanh(0.01 * aux);
    }

    public double Endurance () {
        double aux = chest.getEndurance() + gloves.getEndurance() + helmet.getEndurance() + weapon.getEndurance() + boots.getEndurance();
        return Math.tanh(0.01 * aux);
    }

    public double Life () {
        double aux = chest.getLife() + gloves.getLife() + helmet.getLife() + weapon.getLife() + boots.getLife();
        return 100 * Math.tanh(0.01 * aux);
    }

    public double Atack () {
        return (this.Agility() + this.Expertise()) * this.Strength() * this.ATM();
    }

    public double Defense () {
        return (this.Endurance() + this.Expertise()) * this.Life() * this.DEM();
    }

    public double performance () {
        return 0;
    }

}
