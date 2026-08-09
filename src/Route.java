public class Route {
    private Planet source;
    private Planet destination;

    public Route(Planet source, Planet destination, double distance) {
        this.source = source;
        this.destination = destination;
    }

    public Planet getSource() { return source; }
    public Planet getDestination() { return destination; }
}
