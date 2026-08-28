public class NavigationGraph extends AbstractGraph {

    private static final String[] VALID_PLANETS = {
        "Mercury",
        "Venus",
        "Earth",
        "Mars",
        "Jupiter",
        "Saturn",
        "Uranus",
        "Neptune"
    };

    public NavigationGraph() {
        super();

        createDefaultGraph();
    }

    @Override
    public void addPlanet(String name) {

        boolean validPlanet = false;

        for (String planetName : VALID_PLANETS) {
            if (planetName.equalsIgnoreCase(name)) {
                validPlanet = true;
                name = planetName;
                break;
            }
        }

        if (!validPlanet) {
            Main.feedback("Invalid planet! Please enter a real planet.");
            return;
        }

        if (findPlanet(name) != null) {
            Main.feedback("Planet already exists!");
            return;
        }

        Planet p = new Planet(name);

        planets.add(p);
        adjacencyList.put(p, new java.util.ArrayList<>());

        Main.feedback("Planet added successfully!");
    }

    private void addPlanetWithoutFeedback(String name) {
        Planet p = new Planet(name);

        planets.add(p);
        adjacencyList.put(p, new java.util.ArrayList<>());
    }

    private void addRouteWithoutFeedback(String source, String destination) {
        Planet src = findPlanet(source);
        Planet dest = findPlanet(destination);

        if (src != null && dest != null) {
            adjacencyList.get(src).add(dest);
            adjacencyList.get(dest).add(src);
        }
    }

    private void createDefaultGraph() {
        // Default planets
        addPlanetWithoutFeedback("Earth");
        addPlanetWithoutFeedback("Mars");
        addPlanetWithoutFeedback("Jupiter");
        addPlanetWithoutFeedback("Saturn");

        // Default routes
        addRouteWithoutFeedback("Earth", "Mars");
        addRouteWithoutFeedback("Earth", "Saturn");
        addRouteWithoutFeedback("Mars", "Jupiter");
        addRouteWithoutFeedback("Jupiter", "Saturn");
    }

    public void displayAvailablePlanets() {

        System.out.println("\n" + Main.padded("Planets available to add"));
        System.out.println(Main.padded(Main.seperator(30)));

        boolean available = false;

        for (String planetName : VALID_PLANETS) {

            if (findPlanet(planetName) == null) {
                System.out.println(Main.padded("- " + planetName));
                available = true;
            }
        }

        if (!available) {
            System.out.println(Main.padded("All planets have already been added."));
        }

        System.out.println();
    }

    public void displayExistingPlanets() {

        System.out.println("\n" + Main.padded("Planets currently in graph"));
        System.out.println(Main.padded(Main.seperator(30)));

        if (planets.isEmpty()) {
            System.out.println(Main.padded("No planets in graph."));
        } else {
            for (Planet p : planets) {
                System.out.println(Main.padded("- " + p.getName()));
            }
        }

        System.out.println();
    }
}