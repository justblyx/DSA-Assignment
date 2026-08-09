import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class NavigationGraph {
    private ArrayList<Planet> planets;
    private HashMap<Planet, ArrayList<Planet>> adjacencyList;

    public NavigationGraph() {
        planets = new ArrayList<>();
        adjacencyList = new HashMap<>();
    }

    public ArrayList<Planet> getPlanets() { return planets; }
    public HashMap<Planet, ArrayList<Planet>> getAdjacencyList() { return adjacencyList; }
    
    public void addPlanet(String name) {
        if (findPlanet(name) != null) {
            Main.feedback("Planet already exists!");
            return;
        }
        Planet p = new Planet(name);
        planets.add(p);
        adjacencyList.put(p, new ArrayList<>());
        Main.feedback("Planet added successfully!");
    }

    public void removePlanet(String name) {
        Planet p = findPlanet(name);
        if (p == null) {
            Main.feedback("Planet not found!");
            return;
        }

        for (ArrayList<Planet> neighbours: adjacencyList.values()) {
            neighbours.remove(p);
        }

        planets.remove(p);
        adjacencyList.remove(p);

        Main.feedback("Planet removed successfully!");
    }

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

        adjacencyList.get(src).add(dest);
        adjacencyList.get(dest).add(src);
        
        Main.feedback("Route added successfully!");
    }

    public void removeRoute(String source, String destination) {
        Planet src = findPlanet(source);
        Planet dest = findPlanet(destination);

        if (src == null || dest == null) {
            Main.feedback("One or both planets do not exist!");
            return;
        }

        adjacencyList.get(src).remove(dest);
        adjacencyList.get(dest).remove(src);

        Main.feedback("Route removed successfully!");
    }

    private Planet findPlanet(String name) {
        for (Planet p: planets) {
            if (p.getName().equalsIgnoreCase(name)) return p;
        }
        return null;
    }
    public ArrayList<Planet> bfs(String start) {
        ArrayList<Planet> result = new ArrayList<>();

        Planet startPlanet = findPlanet(start);
        if (startPlanet == null) return result;

        Queue<Planet> queue = new LinkedList<>();
        ArrayList<Planet> visited = new ArrayList<>();

        queue.add(startPlanet);
        visited.add(startPlanet);

        while (!queue.isEmpty()) {
            Planet current = queue.poll();
            
            result.add(current);

            for (Planet neighbour: adjacencyList.get(current)) {
                if (!visited.contains(neighbour)) {
                    visited.add(neighbour);
                    queue.add(neighbour);
                }
            }
        }

        return result;
    }
}
