package Attacks;

import ru.ifmo.se.pokemon.*;

public class dreamEater extends SpecialMove {
    public dreamEater() {
        super(Type.PSYCHIC,100,100);
    }
    private boolean isSleep = false;
    public void applyOppDamage(Pokemon p, double x) {
        if(p.getCondition()==Status.SLEEP) {
            isSleep = true;
            p.setMod(Stat.HP, (int)(Math.round(x)));
        }
    }
    public void applySelfEffects(Pokemon p) {
        if(isSleep) {
            p.setMod(Stat.HP, - (int)((12-p.getHP())/2));
        }
    }
    public String describe() {
        return "использовал Dream Eater";
    }
}
