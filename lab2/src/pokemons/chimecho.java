package pokemons;

import ru.ifmo.se.pokemon.*;
import attacks.*;

public class chimecho extends Pokemon {
    public chimecho(String name, int level) {
        super(name, level);
        setType(Type.PSYCHIC);
        setStats(75,50,80,95,90,65);
        setMove(new recover(), new hypnosis(), new doubleTeam(), new extrasensory());
    }
}