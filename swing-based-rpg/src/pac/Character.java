package pac;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Character extends Entity {
    private Set<Ability> unlockedAbilities = new HashSet<>();
    List<Item> inventory;
    int xp = 10;
    public Character(String name, int xPosition, int yPosition, int hp) {
        super(name, xPosition, yPosition, hp);
    }
    public void setName(String name){
        this.name = name;
    }
    public void setCast(String cast){}

    public Set<Ability> getUnlockedAbilities() {
        return Collections.unmodifiableSet(unlockedAbilities);
    }
    public void unlockAbility(Ability a) {
        unlockedAbilities.add(a);
    }
    public boolean hasAbility(Ability ability) {
        return unlockedAbilities.contains(ability);
    }
    public int getXp(){return xp;}

    public void addXp(int i) {
        xp+=i;
    }
}
