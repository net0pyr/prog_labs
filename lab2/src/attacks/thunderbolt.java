package attacks;

import ru.ifmo.se.pokemon.*;

public class thunderbolt extends SpecialMove {
    public thunderbolt() {
        super(Type.ELECTRIC,90,100);
    }
    public void applyOppEffects(Pokemon p) {
        if(Math.random()<=0.1) {
            Effect.paralyze(p);
        }
    }
    public String describe() {
        return "использовал Thunderbolt";
    }
}
