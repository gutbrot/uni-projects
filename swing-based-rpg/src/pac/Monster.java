package pac;

public class Monster extends Entity {
    int loot;
    int xpReward;
    public Monster(String name, int xPosition, int yPosition, int hp) {
        super(name, xPosition, yPosition,hp);
    }
}
