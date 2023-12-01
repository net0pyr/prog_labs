package attacks;

import ru.ifmo.se.pokemon.*;

public class magicalLeaf extends SpecialMove {
    public magicalLeaf() {
        super(Type.GRASS, 60, 100);
    }
    public void applyOppDamage (Pokemon p, double x) {
        p.setMod(Stat.HP, (int)(x));
    }
    public String describe() {
        return "использовал Magical Leaf";
    }
}
