package Attacks;

import ru.ifmo.se.pokemon.*;

public class Recover extends StatusMove {
    public Recover() {
        super(Type.NORMAL, 0, 100);
    }
    public void applySelfEffects(Pokemon p) {
        p.setMod(Stat.HP, - (int)(12*Math.random()/2));
    }
    public String describe() {
        return "использовал Recover";
    }
}
