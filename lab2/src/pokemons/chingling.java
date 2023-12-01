package pokemons;

import ru.ifmo.se.pokemon.*;
import attacks.*;

public class chingling extends Pokemon {
    public chingling(String name, int level) {
        super(name, level);
        setType(Type.PSYCHIC);
        setStats(45, 30,50,65,50,45);
        setMove(new recover(), new hypnosis(), new doubleTeam());
    }
}