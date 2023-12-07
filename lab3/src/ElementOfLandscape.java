public class ElementOfLandscape extends Landscape implements Disappearing{
    public ElementOfLandscape(String name) {
        super(name);
    }
    public void exist(boolean FlagExist) {
        if(FlagExist)
            System.out.println(this + " не исчез");
        else
            System.out.println(this + " исчез");
    }
    public void see(boolean FlagSee, Person person) {
        if(FlagSee)
            System.out.println(person + " видит " + this);
        else
            System.out.println(person + " не видит " + this);
    }
}
