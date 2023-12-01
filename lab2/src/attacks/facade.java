package Attacks;

import ru.ifmo.se.pokemon.*;

public class facade extends PhysicalMove {
    public facade() {
        super(Type.NORMAL,70,100);
    }
    public void applyOppDamage(Pokemon p, double x) {
        if(p.getCondition()== Status.BURN || p.getCondition()== Status.POISON || p.getCondition()== Status.PARALYZE) {
            p.setMod(Stat.HP, 2*(int)(Math.round(x)));
        }
        else {
            p.setMod(Stat.HP, (int)(Math.round(x)));
        }
    }
    public String describe() {
        return "использовал Facade";
    }
}
