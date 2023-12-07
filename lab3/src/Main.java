public class Main {
    public static void main(String[] args) {
        Person person = new Person("Он");
        person.wipe("глаза");

        ElementOfLandscape river = new ElementOfLandscape("Река");
        ElementOfLandscape trees = new ElementOfLandscape("Деревья");
        Building house = new Building("Дом", "с надписью");
        river.exist(true);
        trees.exist(true);
        house.exist(true);
        System.out.println();

        Shorty[] shorties = new Shorty[4];
        shorties[0]=new Shorty("Небоська", 10, 5);
        shorties[1]=new Shorty("Авоська", 12, 8);
        shorties[2]=new Shorty("Кнопочка", 7, 8);
        shorties[3]=new Shorty("Тюбик", 4, 3);
        shorties[0].see(true, person);
        shorties[1].see(true, person);
        shorties[2].see(true, person);
        shorties[3].see(true, person);
        shorties[0].fight(shorties[1],shorties[(int) Math.round(Math.random()) + 2]);
        shorties[2].fight(shorties[3],shorties[(int) Math.round(Math.random())]);
        System.out.println();

        Person somebody = new Person("кто-то");

        ElementOfLandscape bump = new ElementOfLandscape("кочки");
        ElementOfLandscape ravine = new ElementOfLandscape("овраг");
        Forest forest = new Forest("Лес");
        Swamp swamp = new Swamp("Болото");

        person.think(somebody, Moves.TWEAK, "это");
        person.think(somebody, Moves.FORCE, forest.walk());
        forest.lure(person);
        ResidentsOfLandscape dwarf = new ResidentsOfLandscape("Дворф");
        ResidentsOfLandscape elf = new ResidentsOfLandscape("Эльф");
        dwarf.see(true, person);
        elf.see(true,person);
        if(dwarf.equals(elf)) {
            dwarf.talk(elf);
            System.out.println("Я ЗНАЛ, ЧТО У ВАС, ГРЕБАННЫХ ЭЛЬФОВ, НЕТ ЧЕСТИ!");
            System.out.println("НЕТ УВАЖЕНИЯ!");
            System.out.println("НЕТ ПИВА!");
            elf.talk(dwarf);
            System.out.println("Прощай " + dwarf);
            dwarf.talk(elf);
            System.out.println("Прощай, хорошего денечка");
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
        ElementOfLandscape stone = new ElementOfLandscape("камень");
        person.find(stone);
        swamp.leave(person);

        person.think(somebody, Moves.FORCE, bump.jump());
        person.think(somebody, Moves.FORCE, ravine.slide());
        person.think(somebody, Moves.WANT, "заманить его сюда");
        person.think(somebody, Moves.WANT, "показать нелепую вывеску");
        System.out.println();
    }
}