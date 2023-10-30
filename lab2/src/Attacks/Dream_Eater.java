package Attacks;

import ru.ifmo.se.pokemon.*;

public class Dream_Eater extends SpecialMove {
    public Dream_Eater() {
        super(Type.PSYCHIC,100,100);
    }
    private boolean f = false;
    public void applyOppDamage(Pokemon p, double x) {
        if(p.getCondition()==Status.SLEEP) {
            f = true;
            p.setMod(Stat.HP, (int)(Math.round(x)));
        }
    }
    public void applySelfEffects(Pokemon p) {
        if(f) {
            p.setMod(Stat.HP, - (int)((12-p.getHP())/2));
        }
    }
    public String describe() {
        return "использовал Dream Eater";
    }
}
