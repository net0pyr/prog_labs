public class Elf extends ResidentsOfLandscape{
    public Elf(String name, int hp, int power, String weapon, TypeOfWeapon type) {
        super(name, hp, power, weapon, type);
    }
    public void goodbye(ResidentsOfLandscape resident) {
        System.out.println("Прощай, " + resident);
    }
    public String toString() {
        return "Эльф " + this.getName();
    }
}
