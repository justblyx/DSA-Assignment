import java.util.ArrayList;
import java.util.HashMap;

public interface Graph {
    void addPlanet(String name);
    void removePlanet(String name);
    void addRoute(String source, String destination);
    void removeRoute(String source, String destination);
    ArrayList<Planet> bfs(String start);
    ArrayList<Planet> getPlanets();
    HashMap<Planet, ArrayList<Planet>> getAdjacencyList();
    boolean planetExists(String name);
    ArrayList<Planet> getAdjacentPlanets(String name);
}