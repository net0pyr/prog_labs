package attacks;

import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.StatusMove;
import ru.ifmo.se.pokemon.Type;

public class doubleTeam extends StatusMove {
    public doubleTeam() {
        super(Type.NORMAL,0,100);
    }
    public void applySelfEffects(Pokemon p) {
        p.setMod(Stat.EVASION,1);
    }
    public String describe() {
        return "использовал Double Team";
    }
}
