package Attacks;

import ru.ifmo.se.pokemon.*;

public class hypnosis extends StatusMove {
    public hypnosis() {
        super(Type.PSYCHIC, 0, 60);
    }
    public void applyOppEffects(Pokemon p) {
        Effect e;
        double a = Math.random();
        if(a < 0.33)
            e = new Effect().turns(1).condition(Status.SLEEP);
        else if(a >0.66)
            e = new Effect().turns(2).condition(Status.SLEEP);
        else
            e = new Effect().turns(3).condition(Status.SLEEP);
        p.addEffect(e);
    }
    public String describe() {
        return "использовал Hypnosis";
    }
}
