package Attacks;

import ru.ifmo.se.pokemon.*;

public class Quick_Attack extends PhysicalMove {
    public Quick_Attack() {
        super(Type.NORMAL,40,100);
    }
    public String describe() {
        return "использовал Quick Attack";
    }
}
