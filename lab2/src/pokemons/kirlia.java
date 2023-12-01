package pokemons;

import attacks.*;

public class kirlia extends ralts {
    public kirlia(String name, int level) {
        super(name, level);
        setStats(38,35,35,65,55,50);
        addMove(new magicalLeaf());
    }
}
