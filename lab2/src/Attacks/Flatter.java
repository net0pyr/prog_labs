package Attacks;

import ru.ifmo.se.pokemon.*;
public class Flatter extends StatusMove {
    public Flatter() {
        super(Type.DARK, 0, 100);
    }
    public void applyOppEffects(Pokemon p) {
        p.confuse();
        p.setMod(Stat.SPECIAL_ATTACK,1);
    }
    public String describe() {
        return "использовал Flatter";
    }
}
