package Pokemons;

import ru.ifmo.se.pokemon.*;
import Attacks.*;

public class Chingling extends Pokemon {
    public Chingling (String name, int level) {
        super(name, level);
        setType(Type.PSYCHIC);
        setStats(45, 30,50,65,50,45);
        setMove(new Recover(), new Hypnosis(), new Double_Team());
    }
}