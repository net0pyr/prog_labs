public class ResidentsOfLandscape implements Disappearing{
    private String name;
    private boolean nearby = false;
    private int hp;
    private int power;
    private String weapon;
    private TypeOfWeapon typeThisWeapon;

    public int getHp() {
        return hp;
    }

    public int getPower() {
        return power;
    }

    public void setHp(int hp) {
        this.hp -= hp;
    }

    public void setPower(int power) {
        this.power += power;
    }

    public void fight(ResidentsOfLandscape resident) {
        class Weapon {
            private int powerWeapon;
            private String nameWeapon;
            TypeOfWeapon typeOfWeapon;
            public Weapon(int power, String name, TypeOfWeapon type) {
                this.powerWeapon = power;
                this.nameWeapon = name;
                this.typeOfWeapon = type;
            }
            public String getName() {
                return nameWeapon;
            }
            public TypeOfWeapon getType() {
                return typeOfWeapon;
            }
            public void attack() {
                System.out.println(name + " использовал " + this + " со стихией " + typeOfWeapon);
                switch (typeOfWeapon) {
                    case THUNDER -> {
                        if(Math.random() < 0.5) {
                            int changeHp = power*powerWeapon/100;
                            resident.setHp(changeHp);
                            System.out.println(resident + " потерял " + changeHp + " очков здоровья");
                        }
                        else {
                            System.out.println("Критическое попадание");
                            int changeHp = power*powerWeapon/75;
                            resident.setHp(changeHp);
                            System.out.println(resident + " потерял " + changeHp + " очков здоровья");
                        }
                    }
                    case FIRE -> {
                        if(Math.random() < 0.7) {
                            int changeHp = power*powerWeapon/100;
                            resident.setHp(changeHp);
                            System.out.println(resident + " потерял " + changeHp + " очков здоровья");
                        }
                        else {
                            System.out.println("Критическое попадание");
                            int changeHp = power*powerWeapon/60;
                            resident.setHp(changeHp);
                            System.out.println(resident + " потерял " + changeHp + " очков здоровья");
                        }
                    }
                    case WATER -> {
                        if(Math.random() < 0.8) {
                            int changeHp = power*powerWeapon/100;
                            resident.setHp(changeHp);
                            System.out.println(resident + " потерял " + changeHp + " очков здоровья");
                        }
                        else {
                            System.out.println("Критическое попадание");
                            int changeHp = power*powerWeapon/50;
                            resident.setHp(changeHp);
                            System.out.println(resident + " потерял " + changeHp + " очков здоровья");
                        }
                    }
                }
            }
            public String toString() {
                return nameWeapon;
            }
        }

        Weapon residentsWeapon = new Weapon(50, resident.getWeapon(), resident.getTypeThisWeapon());
        Weapon thisWeapon = new Weapon(50, weapon, typeThisWeapon);
        System.out.println(this + " напал на " + resident);
        System.out.println(this + " достал " + thisWeapon);
        System.out.println(resident + " дотсал " + residentsWeapon);
        boolean flagOfattack = false;
        while(this.getHp() > 0 && resident. getHp() > 0) {
            if(!flagOfattack) {
                thisWeapon.attack();
                System.out.println();
                flagOfattack = true;
            } else {
                residentsWeapon.attack();
                System.out.println();
                flagOfattack = false;
            }
        }
        if(!flagOfattack) {
            System.out.println(name + " победил");
        } else {
            System.out.println(resident + " победил");
        }
    }
    public ResidentsOfLandscape(String name, int hp, int power, String weapon, TypeOfWeapon typeThisWeapon) {
        this.name = name;
        this.hp = hp;
        this.power = power;
        this.weapon = weapon;
        this.typeThisWeapon = typeThisWeapon;
    }
    public ResidentsOfLandscape(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public String getWeapon() {
        return weapon;
    }

    public TypeOfWeapon getTypeThisWeapon() {
        return typeThisWeapon;
    }

    public void exist(boolean FlagExist) {
        if(FlagExist)
            System.out.println(name + " не исчез");
        else
            System.out.println(name + " исчез");
    }
    public void see(boolean FlagSee, Person person) {
        if(FlagSee) {
            System.out.println(person + " видит " + name);
            nearby = true;
        }
        else {
            System.out.println(person + " не видит " + name);
            nearby = false;
        }
    }
    public void talk(ResidentsOfLandscape resident) {
        System.out.println(this + " говорит " + resident + ":");
    }
    public void talkPerson(Person person) {
        System.out.println(this + " говорит " + person + ":");
    }
    public String toString() {
        return name;
    }
    public int hashCode() {
        return (nearby) ? 1 : 0;
    }
}
