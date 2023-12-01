import ru.ifmo.se.pokemon.*;
import pokemons.*;

public class Main {
    public static void main(String[] args) {
        Battle b = new Battle();
        Pokemon p1 = new chingling("Колокольчик", 1);
        Pokemon p2 = new chimecho("Ленточный червь", 1);
        Pokemon p3 = new illumise("Коромыслоголовый", 1);
        Pokemon p4 = new ralts("Эмо", 1);
        Pokemon p5 = new kirlia("Балерина", 1);
        Pokemon p6 = new gallade("Галетина", 1);
        b.addAlly(p3);
        b.addAlly(p1);
        b.addAlly(p2);
        b.addFoe(p4);
        b.addFoe(p5);
        b.addFoe(p6);
        b.go();
    }
}