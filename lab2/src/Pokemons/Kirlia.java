package Pokemons;

import Attacks.*;
import ru.ifmo.se.pokemon.*;

public class Kirlia extends Pokemon {
    public Kirlia (String name, int level) {
        super(name, level);
        setType(Type.PSYCHIC, Type.FAIRY);
        setStats(38,35,35,65,55,50);
        setMove(new Dream_Eater(), new Facade(), new Magical_Leaf());
    }
}
