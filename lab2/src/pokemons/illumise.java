package pokemons;

import ru.ifmo.se.pokemon.*;
import attacks.*;

public class illumise extends Pokemon {
    public illumise(String name, int level) {
        super(name, level);
        setType(Type.BUG);
        setStats(65, 47,75,73,85,85);
        setMove(new flatter(), new rest(), new confuseRay(), new quickAttack());
    }
}
