import ru.ifmo.se.pokemon.*;
import Pokemons.*;

public class Main {
    public static void main(String[] args) {
        Battle b = new Battle();
        Pokemon p1 = new Chingling("Колокольчик", 1);
        Pokemon p2 = new Chimecho("Ленточный червь", 1);
        Pokemon p3 = new Illumise("Коромыслоголовый", 1);
        Pokemon p4 = new Ralts("Эмо", 1);
        Pokemon p5 = new Kirlia("Балерина", 1);
        Pokemon p6 = new Gallade("Галетина", 1);
        b.addAlly(p3);
        b.addAlly(p1);
        b.addAlly(p2);
        b.addFoe(p4);
        b.addFoe(p5);
        b.addFoe(p6);
        b.go();
    }
}