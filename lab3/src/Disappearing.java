public abstract class Disappearing implements Existing{
    private String name;
    public Disappearing(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void exist(boolean FlagExist) {
        if(FlagExist)
            System.out.println(name + " не исчез");
        else
            System.out.println(name + " исчез");
    }

}
