public abstract class Landscape {
    private String name;
    public Landscape(String name) {
        this.name = name;
    }
    public String walk() {
        return ("таскаться по " + name);
    }
    public String jump() {
        return ("прыгать по " + name);
    }
    public String slide() {
        return ("скатиться в " + name);
    }
    public void lure(Person person) {
        System.out.println(this + " заманил " + person);
    }
    public void leave(Person person) {
        System.out.println(person + " покинул " + name);
    }
    public String toString() {
        return name;
    }
}
