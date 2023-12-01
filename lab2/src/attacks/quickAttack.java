package Attacks;

import ru.ifmo.se.pokemon.*;

public class quickAttack extends PhysicalMove {
    public quickAttack() {
        super(Type.NORMAL,40,100);
    }
    public String describe() {
        return "использовал Quick Attack";
    }
}
