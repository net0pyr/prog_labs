package Attacks;

import ru.ifmo.se.pokemon.*;

public class Thunderbolt extends SpecialMove {
    public Thunderbolt() {
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
