public class Shorty implements Disappearing{
    private String name;
    private int power;
    private int defense;
    public Shorty(String name) {
        this.name = name;
    }
    public Shorty(String name, int power, int defense) {
        this.name = name;
        this.power = power;
        this.defense = defense;
    }
    public void setDefense(int defense) {
        this.defense += defense;
    }
    public void setPower(int power) {
        this.power += power;
    }

    public int getDefense() {
        return defense;
    }
    public void exist(boolean FlagExist) {
        if(FlagExist)
            System.out.println(this + " не исчез");
        else
            System.out.println(this + " исчез");
    }
    public void see(boolean FlagSee, Person person) {
        if(FlagSee)
            System.out.println(person +" видит " + this);
        else
            System.out.println(person +" не видит " + this);
    }
    public void fight(Shorty shorty, Shorty watcher) {
        System.out.println(this + " напал на " + shorty);
        if(Math.random() < 0.5) {
            System.out.println(this + " использовал " + watcher + " как живой щит ");
            this.setDefense(watcher.getDefense());
        }
        else {
            System.out.println(shorty + " использовал " + watcher + " как живой щит ");
            shorty.setDefense(watcher.getDefense());
        }
        if(this.hashCode() < shorty.hashCode()) {
            System.out.println(shorty + " поверг в жесткой схватке " + this);
            this.exist(false);
        }
        else if (this.equals(shorty)) {
            System.out.println(this + " поверг в жесткой схватке " + shorty);
            shorty.exist(false);
        }
        else{
            System.out.println(this + " и " + shorty + " упали от изнеможения, бой закончился ничьей");
        }
    }
    public String toString() {
        return "Коротышка " + name;
    }
    public int hashCode() {
        return power + defense;
    }
    public boolean equals(Shorty shorty) {
        return this.hashCode() == shorty.hashCode();
    }
}