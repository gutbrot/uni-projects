package pac;

import org.w3c.dom.*;

import javax.xml.parsers.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class XmlMapLoader {

    public static void loadIntoModel(String path, Model model) throws Exception {

        Document doc = DocumentBuilderFactory
                .newInstance().newDocumentBuilder().parse(new File(path));

        Element root = doc.getDocumentElement();

        World world = loadMap(root);
        loadEnemies(root, world);
        DependencyGraph graph = loadAbilities(root);

        model.setWorld(world);
        model.setGraph(graph);
        System.out.println("XML loaded");
    }

    public static World loadMap(Element root) {
        int rows = Integer.parseInt(root.getAttribute("rows"));
        int cols = Integer.parseInt(root.getAttribute("cols"));

        World world = new World(rows, cols);

        Element mapElement = (Element) root.getElementsByTagName("map").item(0);
        NodeList xmlRows = mapElement.getElementsByTagName("row");

        for (int r = 0; r < rows; r++) {
            String[] values = xmlRows.item(r).getTextContent().trim().split("\\s+");
            for (int c = 0; c < cols; c++) {
                int type = Integer.parseInt(values[c]);
                world.getTiles()[r][c] = new Tile(type, c * 32, r * 32, 32, 32);
            }
        }
        return world;
    }

    private static void loadEnemies(Element root, World world) {
        NodeList enemiesSection = root.getElementsByTagName("enemies");
        if (enemiesSection.getLength() == 0) return;

        Element enemies = (Element) enemiesSection.item(0);
        NodeList enemyNodes = enemies.getElementsByTagName("enemy");

        for (int i = 0; i < enemyNodes.getLength(); i++) {
            Element e = (Element) enemyNodes.item(i);
            String type = e.getAttribute("type");
            int x = Integer.parseInt(e.getAttribute("x"));
            int y = Integer.parseInt(e.getAttribute("y"));
            int hp = Integer.parseInt(e.getAttribute("hp"));
            Monster m = new Monster(type, x, y, hp);
            world.addEntity(m);
        }
    }

    private static DependencyGraph loadAbilities(Element root) {
        NodeList abilityBlock = root.getElementsByTagName("abilities");
        if (abilityBlock.getLength() == 0) return new DependencyGraph();

        Element abilities = (Element) abilityBlock.item(0);
        NodeList abilityNodes = abilities.getElementsByTagName("ability");

        Map<Integer, Ability> map = new HashMap<>();
        DependencyGraph graph = new DependencyGraph();

        for (int i = 0; i < abilityNodes.getLength(); i++) {
            Element e = (Element) abilityNodes.item(i);
            int id = Integer.parseInt(e.getAttribute("id"));
            String name = e.getAttribute("name");
            String desc = e.getAttribute("description");
            int cost = Integer.parseInt(e.getAttribute("cost"));
            int dmg = Integer.parseInt(e.getAttribute("dmg"));
            Ability a = new Ability(id, name, desc, cost, dmg);
            map.put(id, a);
            graph.addAbility(a);
        }

        for (int i = 0; i < abilityNodes.getLength(); i++) {
            Element e = (Element) abilityNodes.item(i);
            int toId = Integer.parseInt(e.getAttribute("id"));
            Ability to = map.get(toId);

            NodeList reqs = e.getElementsByTagName("requires");
            for (int r = 0; r < reqs.getLength(); r++) {
                int fromId = Integer.parseInt(reqs.item(r).getTextContent().trim());
                graph.addDependency(map.get(fromId), to);
            }
        }
        return graph;
    }
}