package pokemons;

import attacks.*;
import ru.ifmo.se.pokemon.*;

public class gallade extends kirlia {
    public gallade(String name, int level) {
        super(name, level);
        addType(Type.FIGHTING);
        setStats(68,125,65,65,115,80);
        addMove(new thunderbolt());
    }
}
