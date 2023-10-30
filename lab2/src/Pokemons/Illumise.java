package Pokemons;

import ru.ifmo.se.pokemon.*;
import Attacks.*;

public class Illumise extends Pokemon {
    public Illumise (String name, int level) {
        super(name, level);
        setType(Type.BUG);
        setStats(65, 47,75,73,85,85);
        setMove(new Flatter(), new Rest(), new Confuse_Ray(), new Quick_Attack());
    }
}
