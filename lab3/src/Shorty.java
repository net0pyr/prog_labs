public class Shorty extends Disappearing{
    public Shorty(String name) {
        super(name);
    }
    public void exist(boolean FlagExist) {
        System.out.print("Коротышка ");
        super.exist(FlagExist);
    }
    public int hashCode() {
        return this.getName().hashCode();
    }
    public boolean equals(Shorty shorty) {
        return this.hashCode() == shorty.hashCode();
    }
}
