import java.util.ArrayList;
import java.util.HashMap;

public interface Graph {
    boolean addPlanet(String name);
    boolean removePlanet(String name);
    boolean addRoute(String source, String destination);
    boolean removeRoute(String source, String destination);
    ArrayList<Planet> bfs(String start);
    ArrayList<Planet> getPlanets();
    HashMap<Planet, ArrayList<Planet>> getAdjacencyList();
    boolean planetExists(String name);
    ArrayList<Planet> getAdjacentPlanets(String name);
}