package pac;

import java.util.ArrayList;
import java.util.List;

public class Tile {
    public int x,y, width, height;
    private int type;
    List<Entity> entities = new ArrayList<>();

    public Tile(int type,int xPosition, int yPosition, int width, int height) {
        this.type = type;
        x = xPosition;
        y = yPosition;
        this.width = width;
        this.height = height;
    }

    public void setType(int type){
        this.type = type;
    }
    public int getType() {
        return type;
    }
    public void addEntity(Entity e){
        entities.add(e);
    }
    public void removeEntity(Entity e){
        entities.remove(e);
    }
    public int entityCount(){
        return entities.size();
    }
    public List<Entity> getEntities(){
        return entities;
    }
}