package attacks;

import ru.ifmo.se.pokemon.*;

public class confuseRay extends StatusMove {
    public confuseRay() {
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
