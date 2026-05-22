package pac;

public class Model {
    private World world;
    private DependencyGraph graph;
    private Character player;

    public Model(){
        System.out.println("New Model");
    }

    public World getWorld() { return world; }
    public void setWorld(World world) {
        this.world = world;
        System.out.println("World set");
    }

    public DependencyGraph getGraph() { return graph; }
    public void setGraph(DependencyGraph graph) {
        this.graph = graph;
        System.out.println("Graph set");
    }

    public Character getPlayer() { return player; }
    public void setPlayer(Character player) {
        this.player = player;
        System.out.println("Character set");
    }
}
