import pac.Ability;
import pac.DependencyGraph;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

public class AbilityTest {
    private Ability slash;
    private Ability parry;
    private Ability whirlwind;

    @BeforeEach
    public void setUp() {
        slash = new Ability(1, "Slash", "Basic melee attack", 0, 8);
        parry = new Ability(2, "Parry", "Defensive stance", 50, 0);
        whirlwind = new Ability(3, "Whirlwind", "Attack all enemies", 100, 50);
    }

    @Test
    public void testUnlockableAbilities() {
        DependencyGraph graph = new DependencyGraph();

        graph.addAbility(slash);
        graph.addAbility(parry);
        graph.addAbility(whirlwind);

        graph.addDependency(slash, parry);
        graph.addDependency(parry, whirlwind);

        Set<Ability> unlocked = new HashSet<>();
        unlocked.add(slash);

        Set<Ability> unlockable = graph.getUnlockable(unlocked);

        assertTrue(unlockable.contains(parry));
        assertFalse(unlockable.contains(whirlwind));
    }

    @Test
    public void testCanUnlockRequiresAllPrerequisites() {
        DependencyGraph graph = new DependencyGraph();

        graph.addAbility(slash);
        graph.addAbility(parry);
        graph.addAbility(whirlwind);

        graph.addDependency(slash, parry);
        graph.addDependency(parry, whirlwind);

        Set<Ability> unlocked = new HashSet<>();
        unlocked.add(slash);

        assertTrue(graph.canUnlock(parry, unlocked));

        assertFalse(graph.canUnlock(whirlwind, unlocked));

        unlocked.add(parry);
        assertTrue(graph.canUnlock(whirlwind, unlocked));
    }

    @Test
    public void testGetParentsReturnsCorrectPrerequisites() {
        DependencyGraph graph = new DependencyGraph();

        graph.addAbility(slash);
        graph.addAbility(parry);
        graph.addAbility(whirlwind);

        graph.addDependency(slash, parry);
        graph.addDependency(slash, whirlwind);
        graph.addDependency(parry, whirlwind);

        Set<Ability> parentsOfParry = graph.getParents(parry);
        Set<Ability> parentsOfWhirlwind = graph.getParents(whirlwind);

        assertEquals(1, parentsOfParry.size());
        assertTrue(parentsOfParry.contains(slash));

        assertEquals(2, parentsOfWhirlwind.size());
        assertTrue(parentsOfWhirlwind.contains(slash));
        assertTrue(parentsOfWhirlwind.contains(parry));
    }
}
