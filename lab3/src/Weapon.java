public class Weapon {
    private int power;
    private String name;
    TypeOfWeapon type;
    public Weapon(int power, String name) {
        this.power = power;
        this.name = name;
    }
    public Weapon(int power, String name, TypeOfWeapon type) {
        this.power = power;
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }
    public TypeOfWeapon getType() {
        return type;
    }
}
