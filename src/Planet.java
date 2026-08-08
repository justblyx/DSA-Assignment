public class Planet {
    private String name;

    public Planet(String name) {
        this.name = name;
    }

    public String getName() { return name; }
    
    @Override
    public String toString() { return name; }
}
