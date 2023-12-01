public class Main {
    public static void main(String[] args) {
        Person person = new Person("Он");
        person.wipe("глаза");

        Object river = new Object("Река");
        Object trees = new Object("Деревья");
        Building house = new Building("Дом", "с надписью");
        river.exist(true);
        trees.exist(true);
        house.exist(true);

        Shorty[] shorties = new Shorty[4];
        shorties[0]=new Shorty("Небоська");
        shorties[1]=new Shorty("Авоська");
        shorties[2]=new Shorty("Кнопочка");
        shorties[3]=new Shorty("Тюбик");
        shorties[0].exist(true);
        shorties[1].exist(true);
        shorties[2].exist(true);
        shorties[3].exist(true);

        Person somebody = new Person("кто-то");
        person.think(somebody, Moves.tweak, "это");
        person.think(somebody, Moves.force, "таскаться по лесам и болотам");
        person.think(somebody, Moves.force, "прыгать по кочкам");
        person.think(somebody, Moves.force, "скатываться в овраг");
        person.think(somebody, Moves.want, "заманить его сюда");
        person.think(somebody, Moves.want, "показать нелепую вывеску");
        System.out.println();
    }
}