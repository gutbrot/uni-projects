import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pac.Ability;
import pac.Character;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class CharacterTest {

    private Character player;

    @BeforeEach
    public void setup() {
        player = new Character("Hero", 2, 3, 20);
    }

    @Test
    public void testSetName() {
        player.setName("Knight");
        assertEquals("Knight", player.getName());
    }

    @Test
    public void testSetPos() {
        player.setPos(5, 7);
        assertEquals(5, player.getX());
        assertEquals(7, player.getY());
    }

    @Test
    public void testHpDecrease() {
        player.setHp(20);
        player.setHp(player.getHp() - 5);
        assertEquals(15, player.getHp());
    }

    @Test
    public void testUnlockAbility() {
        Ability slash = new Ability(1, "Slash", "Basic attack", 10, 5);

        assertFalse(player.getUnlockedAbilities().contains(slash));

        player.unlockAbility(slash);

        assertTrue(player.getUnlockedAbilities().contains(slash));
    }

    @Test
    public void testUnlockAbility_NoDuplicates() {
        Ability a = new Ability(1, "Slash", "Basic attack", 10, 5);

        player.unlockAbility(a);
        player.unlockAbility(a);

        Set<Ability> set = player.getUnlockedAbilities();

        assertEquals(1, set.size());
        assertTrue(set.contains(a));
    }
}
