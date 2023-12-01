import java.nio.MappedByteBuffer;

public class Building extends Disappearing{
    private String appearance;
    public Building(String name, String appearance) {
        super(name);
        this.appearance = appearance;
    }
    public Building(String name) {
        super(name);
    }
    public void exist(boolean FlagExist) {
        if(FlagExist)
            System.out.println(getName() + " " + appearance + " не исчез");
        else
            System.out.println(getName() + " " + appearance + " исчез");
    }
}
