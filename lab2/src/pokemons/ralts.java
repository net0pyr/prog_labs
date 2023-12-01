package pokemons;

import attacks.*;
import ru.ifmo.se.pokemon.*;

public class ralts extends Pokemon {
    public ralts(String name, int level) {
        super(name, level);
        setType(Type.PSYCHIC, Type.FAIRY);
        setStats(28,25,25,45,35,40);
        setMove(new dreamEater(), new facade());
    }
}
