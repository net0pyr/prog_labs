public class Dwarf extends ResidentsOfLandscape{
    public Dwarf(String name, int hp, int power, String weapon, TypeOfWeapon type) {
        super(name, hp, power, weapon, type);
    }
    public void goodbye() {
        System.out.println("Прощай, хорошего денечка");
    }
    @Override
    public String toString() {
        return "Дворф " + this.getName();
    }
}
