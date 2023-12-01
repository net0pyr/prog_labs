package attacks;

import ru.ifmo.se.pokemon.*;
public class flatter extends StatusMove {
    public flatter() {
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
