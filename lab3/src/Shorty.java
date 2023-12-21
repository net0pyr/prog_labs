public class Shorty implements Disappearing {
    private String name;
    private int power;
    private int defense;
    private boolean life = true;
    private static int countShorties = 0;
    public Shorty(String name) {
        countShorties++;
        this.name = name;
    }

    public static int getCountShorties() {
        return countShorties;
    }

    public String getName() {
        return name;
    }

    public int getPower() {
        return power;
    }

    public boolean isLife() {
        return life;
    }

    public void setLife(boolean life) {
        this.life = life;
    }

    public Shorty(String name, int power, int defense) {
        this.name = name;
        this.power = power;
        this.defense = defense;
        countShorties++;
    }

    public void setDefense(int defense) {
        this.defense += defense;
    }

    public void setPower(int power) {
        this.power += power;
    }

    public int getDefense() {
        return defense;
    }

    public void exist(boolean FlagExist) {
        if (FlagExist)
            System.out.println(this + " не исчез");
        else
            System.out.println(this + " исчез");
    }

    public void see(boolean FlagSee, Person person) {
        if (FlagSee)
            System.out.println(person + " видит " + this);
        else
            System.out.println(person + " не видит " + this);
    }

    public static void goFight(int indexShorty, Shorty[] shorties, Shorty shorty) throws FightException {
        if (!shorties[indexShorty].isLife()) {
            throw new FightException(shorty + " пытался бросить " + shorties[indexShorty] + ". Мертвыми коротышками кидаться нельзя. Это аморально.");
        }
    }

    public void shortyShield(Shorty shorty, int indexShield, Shorty[] shorties) {
        System.out.println(shorty + " использовал " + shorties[indexShield] + " как живой щит");
        shorty.setDefense(shorties[indexShield].getDefense());
    }
    public void shortyAttack(Shorty shorty, int indexAttack, Shorty[] shorties) {
        try {
            goFight(indexAttack, shorties, this);
        } catch (FightException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
        System.out.println(this + " бросил наблюдающего за боем " + shorties[indexAttack] + " в " + shorty);
        this.setPower(shorties[indexAttack].getPower());
    }

    public void fight(Shorty shorty, Shorty[] shorties) {
        System.out.println(this + " вызвал " + shorty + " на бой");
        int indexThis = 0, indexShorty = 0;
        for (int i = 0; i < shorties.length; i++) {
            if (this.equals(shorties[i]))
                indexThis = i;
            if (shorty.equals(shorties[i]))
                indexShorty = i;
        }
        int indexShield = (int) (Math.random() * shorties.length);
        while (indexShield == indexShorty || indexShield == indexThis)
            indexShield = (int) (Math.random() * shorties.length);
        int indexAttack = (int) (Math.random() * shorties.length);
        while (indexAttack == indexShorty || indexAttack == indexThis || indexAttack == indexShield)
            indexAttack = (int) (Math.random() * shorties.length);
        boolean flagDefense = false;
        if (Math.random() < 0.5) {
            this.shortyShield(shorty, indexShield, shorties);
        } else {
            shorty.shortyShield(this, indexShield, shorties);
            flagDefense = true;
        }
        if (flagDefense) {
            this.shortyAttack(shorty, indexAttack, shorties);
        } else {
            shorty.shortyAttack(this, indexAttack, shorties);
        }
        if (this.hashCode() > shorty.hashCode()) {
            System.out.println(this + " поверг " + shorty);
            shorty.setLife(false);
            countShorties--;
        } else if (this.hashCode() < shorty.hashCode()) {
            System.out.println(shorty + " поверг " + this);
            this.setLife(false);
            countShorties--;
        } else {
            System.out.println(shorty + " и " + this + " остались живы, но упали от изнеможения");
        }
        if (flagDefense) {
            this.setPower(-shorties[indexAttack].getPower());
            shorty.setDefense(-shorties[indexShield].getDefense());
        } else {
            shorty.setPower(-shorties[indexAttack].getPower());
            this.setDefense(-shorties[indexShield].getDefense());
        }
        System.out.println();
    }

    public String toString() {
        return "Коротышка " + name;
    }

    public int hashCode() {
        return power + defense;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        Shorty shorty = (Shorty) obj;
        return name.equals(shorty.getName());
    }

}