public enum TypeOfWeapon {
    FIRE("огонь"),
    WATER("водa"),
    THUNDER("молния");
    private String title;
    TypeOfWeapon(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
