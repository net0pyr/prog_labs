public class ResidentsOfLandscape implements Disappearing{
    private String name;
    private boolean nearby = false;
    public ResidentsOfLandscape(String name) {
        this.name = name;
    }
    public void exist(boolean FlagExist) {
        if(FlagExist)
            System.out.println(name + " не исчез");
        else
            System.out.println(name + " исчез");
    }
    public void see(boolean FlagSee, Person person) {
        if(FlagSee) {
            System.out.println(person + " видит " + name);
            nearby = true;
        }
        else {
            System.out.println(person + " не видит " + name);
            nearby = false;
        }
    }
    public void talk(ResidentsOfLandscape resident) {
        System.out.println(this + " говорит " + resident + ":");
    }
    public void talkPerson(Person person) {
        System.out.println(this + " говорит " + person + ":");
    }
    public String toString() {
        return name;
    }
    public int hashCode() {
        return (nearby) ? 1 : 0;
    }
    public boolean equals(ResidentsOfLandscape resident) {
        return this.hashCode() == resident.hashCode();
    }
}
