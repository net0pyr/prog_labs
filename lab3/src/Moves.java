public enum Moves {
    TWEAK("подстроил"),
    FORCE("заставил"),
    WANT("хочет");
    private String title;
    Moves (String title) {
        this.title = title;
    }
    public String toString() {
        return title;
    }
}
