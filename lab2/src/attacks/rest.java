package attacks;

import ru.ifmo.se.pokemon.*;

public class rest extends StatusMove {
    public rest() {
        super(Type.PSYCHIC, 0, 100);
    }
    public void applySelfEffects(Pokemon p) {
        Effect e = new Effect().turns(2).condition(Status.SLEEP);
        double a = 12.45-p.getHP();
        p.setMod(Stat.HP, - (int)(Math.round(a)));
        p.addEffect(e);
    }
    public String describe() {
        return "использовал Rest";
    }
}
