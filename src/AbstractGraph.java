import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public abstract class AbstractGraph implements Graph {

    protected ArrayList<Planet> planets;
    protected HashMap<Planet, ArrayList<Planet>> adjacencyList;

    public AbstractGraph() {
        planets = new ArrayList<>();
        adjacencyList = new HashMap<>();
    }

    @Override
    public ArrayList<Planet> getPlanets() {
        return planets;
    }

    @Override
    public HashMap<Planet, ArrayList<Planet>> getAdjacencyList() {
        return adjacencyList;
    }

    protected Planet findPlanet(String name) {
        for (Planet p : planets) {
            if (p.getName().equalsIgnoreCase(name)) {
                return p;
            }
        }

        return null;
    }

    @Override
    public boolean planetExists(String name) {
        return findPlanet(name) != null;
    }

    @Override
    public ArrayList<Planet> getAdjacentPlanets(String name) {
        Planet planet = findPlanet(name);

        if (planet == null) {
            return null;
        }

        return adjacencyList.get(planet);
    }

    @Override
    public void removePlanet(String name) {
        Planet p = findPlanet(name);

        if (p == null) {
            Main.feedback("Planet does not exist!");
            return;
        }

        // Remove the planet from other adjacency lists
        for (ArrayList<Planet> neighbours : adjacencyList.values()) {
            neighbours.remove(p);
        }

        // Remove the planet itself
        planets.remove(p);
        adjacencyList.remove(p);

        Main.feedback("Planet removed successfully!");
    }

    @Override
    public void addRoute(String source, String destination) {
        Planet src = findPlanet(source);
        Planet dest = findPlanet(destination);

        if (src == null || dest == null) {
            Main.feedback("One or both planets do not exist!");
            return;
        }

        if (src.equals(dest)) {
            Main.feedback("A planet cannot be connected to itself!");
            return;
        }

        if (adjacencyList.get(src).contains(dest)) {
            Main.feedback("Route already exists!");
            return;
        }

        // Undirected graph
        adjacencyList.get(src).add(dest);
        adjacencyList.get(dest).add(src);

        Main.feedback("Route added successfully!");
    }

    @Override
    public void removeRoute(String source, String destination) {
        Planet src = findPlanet(source);
        Planet dest = findPlanet(destination);

        if (src == null || dest == null) {
            Main.feedback("One or both planets do not exist!");
            return;
        }

        if (!adjacencyList.get(src).contains(dest)) {
            Main.feedback("Route does not exist!");
            return;
        }

        adjacencyList.get(src).remove(dest);
        adjacencyList.get(dest).remove(src);

        Main.feedback("Route removed successfully!");
    }

    @Override
    public ArrayList<Planet> bfs(String start) {
        ArrayList<Planet> result = new ArrayList<>();

        Planet startPlanet = findPlanet(start);

        if (startPlanet == null) {
            return result;
        }

        Queue<Planet> queue = new LinkedList<>();
        ArrayList<Planet> visited = new ArrayList<>();

        queue.add(startPlanet);
        visited.add(startPlanet);

        while (!queue.isEmpty()) {
            Planet current = queue.poll();

            result.add(current);

            for (Planet neighbour : adjacencyList.get(current)) {
                if (!visited.contains(neighbour)) {
                    visited.add(neighbour);
                    queue.add(neighbour);
                }
            }
        }

        return result;
    }
}