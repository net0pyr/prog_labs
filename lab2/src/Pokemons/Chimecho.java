package Pokemons;

import ru.ifmo.se.pokemon.*;
import Attacks.*;

public class Chimecho extends Pokemon {
    public Chimecho (String name, int level) {
        super(name, level);
        setType(Type.PSYCHIC);
        setStats(75,50,80,95,90,65);
        setMove(new Recover(), new Hypnosis(), new Double_Team(), new Extrasensory());
    }
}