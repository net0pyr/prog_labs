public enum Moves {
    tweak("подстроил"),
    force("заставил"),
    want("хочет");
    private String title;
    Moves (String title) {
        this.title = title;
    }
    public String toString() {
        return title;
    }
}
