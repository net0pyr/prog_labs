public class Building implements Disappearing{
    private String name;
    private String appearance;
    public Building(String name, String appearance) {
        this.name = name;
        this.appearance = appearance;
    }
    public Building(String name) {
        this.name = name;
    }
    public void setAppearance(String appearance) {
        this.appearance = appearance;
    }

    public void see(boolean FlagSee, Person person) {
        if(FlagSee)
            System.out.println(person + " видит " + this);
        else
            System.out.println(person + " не видит " + this);
    }

    public void exist(boolean FlagExist) {
        if(FlagExist)
            System.out.println(this + " не исчез");
        else
            System.out.println(this + " исчез");
    }

    public String toString() {
        return name + " " + appearance;
    }
}
