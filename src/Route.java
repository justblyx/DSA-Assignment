public class Route {
    private Planet source;
    private Planet destination;
    private double distance;

    public Route(Planet source, Planet destination, double distance) {
        this.source = source;
        this.destination = destination;
        this.distance = distance;
    }

    public Planet getSource() { return source; }
    public Planet getDestination() { return destination; }
    public double getDistance() { return distance; }
}
