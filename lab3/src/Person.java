public class Person implements Wiping{
    private String name;
    public Person(String name) {
        this.name = name;
    }
    public void wipe(String ObjectOfWiping) {
        System.out.println(name+" протирает "+ObjectOfWiping);
    }
    public void think(Person person, Moves move, String motionParameters) {
        System.out.print(name + " считает, что ");
        switch (move) {
            case want:
                want(person, move, motionParameters);
                break;
            case force:
                force(person, this, move, motionParameters);
                break;
            case tweak:
                person.tweak(person, move, motionParameters);
                break;
        }
    }
    public void tweak(Person person, Moves move, String action) {
        System.out.println(person + " " + move +  " " + action);
    }
    public void want(Person person, Moves move, String wish) {
        System.out.println(person + " " + move +  " " + wish);
    }
    public void force (Person person, Person executor, Moves move, String action) {
        System.out.println(person + " " + move +  " " + executor + " " + action);
    }

    public String toString() {
        return name;
    }
}
