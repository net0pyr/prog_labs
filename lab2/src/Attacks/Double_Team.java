package Attacks;

import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.StatusMove;
import ru.ifmo.se.pokemon.Type;

public class Double_Team extends StatusMove {
    public Double_Team() {
        super(Type.NORMAL,0,100);
    }
    public void applySelfEffects(Pokemon p) {
        p.setMod(Stat.EVASION,1);
    }
    public String describe() {
        return "использовал Double Team";
    }
}
