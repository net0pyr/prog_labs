package attacks;

import ru.ifmo.se.pokemon.*;

public class extrasensory extends SpecialMove {
    public extrasensory() {
        super(Type.PSYCHIC, 80, 100);
    }
    public void applyOppEffects(Pokemon p) {
        if(Math.random()<=0.1) {
            Effect.flinch(p);
        }
    }
    public String describe() {
        return "использовал Extrasensory";
    }
}
