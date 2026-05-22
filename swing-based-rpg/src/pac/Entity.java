package pac;

public class Entity {
    int id;
    String name;
    int xPos, yPos;
    int hp, maxHp;
    int atk;
    int def;
    int speed;
    boolean isDead;

    public Entity(String name, int xPosition, int yPosition, int hp) {
        this.name = name;
        this.xPos = xPosition;
        this.yPos = yPosition;
        this.hp = hp;
    }
    public String getName(){
        return name;
    }
    public int getX(){
        return xPos;
    }
    public int getY(){
        return yPos;
    }
    public void setPos(int nx, int ny){ xPos=nx; yPos=ny; }
    public int getHp(){return hp;}
    public void setHp(int hp){this.hp = hp;}

    @Override
    public String toString(){
        return name;
    }
}
