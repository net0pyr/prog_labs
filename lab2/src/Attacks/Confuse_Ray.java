package Attacks;

import ru.ifmo.se.pokemon.*;

public class Confuse_Ray extends StatusMove {
    public Confuse_Ray() {
        super(Type.GHOST, 0,100);
    }
    public void applyOppEffects(Pokemon p) {
        p.confuse();
        p.setMod(Stat.SPECIAL_ATTACK,0);
    }
    public String describe() {
        return "использовал Confuse Ray";
    }
}
