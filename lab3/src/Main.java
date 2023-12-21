public class Main {
    public static void main(String[] args) {
        Survivor<Shorty> shortySurvivor = (Shorty[] shorties) -> {
            Shorty survivor = new Shorty("");
            for(Shorty shorty : shorties) {
                if(shorty.isLife())
                    survivor = shorty;
            }
            System.out.println(survivor + " - единственный выживший коротышка");
        };
        Person person = new Person("Он");
        person.wipe("глаза");

        ElementOfLandscape river = new ElementOfLandscape("Река");
        ElementOfLandscape trees = new ElementOfLandscape("Деревья");
        Building house = new Building("Дом", "с надписью");
        river.exist(true);
        trees.exist(true);
        house.exist(true);
        System.out.println();

        Shorty[] shorties = new Shorty[10];
        shorties[0]=new Shorty("Небоська", 10, 5);
        shorties[1]=new Shorty("Авоська", 12, 8);
        shorties[2]=new Shorty("Кнопочка", 7, 8);
        shorties[3]=new Shorty("Тюбик", 6, 5);
        shorties[4]=new Shorty("Храпун", 6, 10);
        shorties[5]=new Shorty("Голубчик", 7, 9);
        shorties[6]=new Shorty("Пельменчик", 10, 10);
        shorties[7]=new Shorty("Дурачок", 14, 3);
        shorties[8]=new Shorty("Толстяк", 5, 15);
        shorties[9]=new Shorty("Трубочист", 20, 0);

        shorties[0].see(true, person);
        shorties[1].see(true, person);
        shorties[2].see(true, person);
        shorties[3].see(true, person);
        shorties[4].see(true, person);
        shorties[5].see(true, person);
        shorties[6].see(true, person);
        shorties[7].see(true, person);
        shorties[8].see(true, person);
        shorties[9].see(true, person);
        System.out.println();

        while(Shorty.getCountShorties()>1) {
            int indexFighter1 = (int) (Math.random()*shorties.length);
            int indexFighter2 = (int) (Math.random()*shorties.length);
            while(indexFighter1 == indexFighter2 || !shorties[indexFighter1].isLife() || !shorties[indexFighter2].isLife()) {
                indexFighter1 = (int) (Math.random()*shorties.length);
                indexFighter2 = (int) (Math.random()*shorties.length);
            }
            shorties[indexFighter1].fight(shorties[indexFighter2], shorties);
        }
        shortySurvivor.lastSurvivor(shorties);
        System.out.println();

        Person somebody = new Person("кто-то");

        ElementOfLandscape bump = new ElementOfLandscape("кочки");
        ElementOfLandscape ravine = new ElementOfLandscape("овраг");
        Forest forest = new Forest("Лес");
        Swamp swamp = new Swamp("Болото");

        person.think(somebody, Moves.TWEAK, "это");
        person.think(somebody, Moves.FORCE, forest.walk());
        forest.lure(person);
        Dwarf dwarf = new Dwarf("Глоин Огненная Борода", 20, 15, "Секира", TypeOfWeapon.FIRE);
        Elf elf = new Elf("Элендил Серебряный Лист", 30, 10, "Лук", TypeOfWeapon.THUNDER);
        dwarf.see(true, person);
        elf.see(true,person);
        if(dwarf.hashCode() == elf.hashCode()) {
            dwarf.talk(elf);
            System.out.println("Я ЗНАЛ, ЧТО У ВАС, ГРЕБАННЫХ ЭЛЬФОВ, НЕТ ЧЕСТИ!\nНЕТ УВАЖЕНИЯ!\nНЕТ ПИВА!");

            System.out.println();
            elf.fight(dwarf);

            elf.talk(dwarf);
            elf.goodbye(dwarf);
            dwarf.talk(elf);
            dwarf.goodbye();
            System.out.println();
        }
        forest.leave(person);

        person.think(somebody, Moves.FORCE, swamp.walk());
        swamp.lure(person);
        ResidentsOfLandscape monster = new ResidentsOfLandscape("Болотный монстр");
        ElementOfLandscape dam = new ElementOfLandscape("плотина");
        dam.see(true, person);
        monster.see(true, person);
        monster.talkPerson(person);
        System.out.println("Плотину надо поднять. Рычагом. Я его дам. Канал нужно завалить камнем. Камень я не дам.");
        Lost lostStone = new Lost() {
            @Override
            public void lost() {
                System.out.println("Камень потерялся в " + swamp);
            }
            @Override
            public void hide() {
                System.out.println(monster + " спрятал камень в глубине " + swamp);
            }
            @Override
            public void pickUp() {
                System.out.println(person + " нашел и подобрал камень");
            }
        };
        lostStone.lost();
        lostStone.hide();
        lostStone.pickUp();
        swamp.leave(person);

        person.think(somebody, Moves.FORCE, bump.jump());
        person.think(somebody, Moves.FORCE, ravine.slide());
        person.think(somebody, Moves.WANT, "заманить его сюда");
        person.think(somebody, Moves.WANT, "показать нелепую вывеску");

    }
}