package Pokemons;

import Attacks.*;
import ru.ifmo.se.pokemon.*;

public class Gallade extends Pokemon {
    public Gallade (String name, int level) {
        super(name, level);
        setType(Type.PSYCHIC, Type.FIGHTING);
        setStats(68,125,65,65,115,80);
        setMove(new Dream_Eater(), new Facade(), new Magical_Leaf(), new Thunderbolt());
    }
}
