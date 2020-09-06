public class Player {
    private double height;
    private Item chest;
    private Item gloves;
    private Item helmet;
    private Item weapon;
    private Item boots;
    private String type;

    public Player(Player p) {
        this(p.getHeight(), p.getChest(), p.getGloves(), p.getHelmet(), p.getWeapon(), p.getBoots(), p.getType());
    }

    public Player(double height, Item chest, Item gloves, Item helmet, Item weapon, Item boots, String type) {
        this.height = height;
        this.chest = chest;
        this.gloves = gloves;
        this.helmet = helmet;
        this.weapon = weapon;
        this.boots = boots;
        this.type = type;
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
        switch(type) {
            case "warrior":
                return 0.6 * this.Atack() + 0.6 * this.Defense();
            case "archer":
                return 0.9 * this.Atack() + 0.1 * this.Defense();
            case "defender":
                return 0.3 * this.Atack() + 0.8 * this.Defense();
            case "infiltrate":
                return 0.8 * this.Atack() + 0.3 * this.Defense();
        }
        return 0;
    }

    public double getHeight() {
        return height;
    }

    public Item getChest() {
        return chest;
    }

    public Item getHelmet() {
        return helmet;
    }

    public Item getGloves() {
        return gloves;
    }

    public Item getBoots() {
        return boots;
    }

    public Item getWeapon() {
        return weapon;
    }

    public String getType() {
        return type;
    }

    public void setBoots(Item boots) {
        this.boots = boots;
    }

    public void setChest(Item chest) {
        this.chest = chest;
    }

    public void setGloves(Item gloves) {
        this.gloves = gloves;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setHelmet(Item helmet) {
        this.helmet = helmet;
    }

    public void setWeapon(Item weapon) {
        this.weapon = weapon;
    }

    public double[] getGens() {
        double[] aux = new double[6];
        aux[0] = this.height;
        aux[1] = this.chest.getId();
        aux[2] = this.gloves.getId();
        aux[3] = this.helmet.getId();
        aux[4] = this.weapon.getId();
        aux[5] = this.boots.getId();
        return aux;
    }
}
