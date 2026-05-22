package pac;

import java.util.*;

public class DependencyGraph {
    private final Map<Ability, Set<Ability>> adjList = new HashMap<>();

    public void addAbility(Ability a) {
        adjList.putIfAbsent(a, new HashSet<>());
    }

    public void addDependency(Ability parent, Ability child) {
        addAbility(parent);
        addAbility(child);
        adjList.get(parent).add(child);
    }

    public Set<Ability> getChildren(Ability a) {
        return adjList.getOrDefault(a, Collections.emptySet());
    }

    public Set<Ability> getParents(Ability a) {
        Set<Ability> parents = new HashSet<>();
        for (var entry : adjList.entrySet()) {
            if (entry.getValue().contains(a)) {
                parents.add(entry.getKey());
            }
        }
        return parents;
    }

    public boolean canUnlock(Ability a, Set<Ability> unlocked) {
        for (Ability prerequisite : getParents(a)) {
            if (!unlocked.contains(prerequisite)) {
                return false;
            }
        }
        return true;
    }

    public Set<Ability> getUnlockable(Set<Ability> unlocked) {
        Set<Ability> unlockable = new HashSet<>();
        for (Ability a : adjList.keySet()) {
            if (!unlocked.contains(a) && canUnlock(a, unlocked)) {
                unlockable.add(a);
            }
        }
        return unlockable;
    }

    public Set<Ability> getUnlocked(Set<Ability> unlocked) {
        Set<Ability> result = new HashSet<>();
        for (Ability a : adjList.keySet()) {
            if (unlocked.contains(a)) {
                result.add(a);
            }
        }
        return result;
    }
    public Collection<Ability> getAllAbilities() {
        return adjList.keySet();
    }
}