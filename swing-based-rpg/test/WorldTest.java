import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pac.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WorldTest {

    private World world;
    private pac.Character player;

    @BeforeEach
    public void setup() {
        world = new World(5, 5);

        for (int r = 0; r < 5; r++) {
            for (int c = 0; c < 5; c++) {
                world.getTiles()[r][c] = new Tile(0, c, r, 32, 32);
            }
        }

        player = new pac.Character("Hero", 2, 2,10);
        world.addEntity(player);
    }

    @Test
    public void testMovePlayer_ValidMove() {
        MoveResult result = world.movePlayer(player, 1, 0);

        assertEquals(MoveResult.VALID, result);
        assertEquals(3, player.getX());
        assertEquals(2, player.getY());
    }

    @Test
    public void testMovePlayer_WallBlocked() {
        world.setTileType(3, 2, 1);

        MoveResult result = world.movePlayer(player, 0, 1);

        assertEquals(MoveResult.INVALID, result);
        assertEquals(2, player.getX());
        assertEquals(2, player.getY());
    }

    @Test
    public void testMovePlayer_OutOfBounds() {
        MoveResult result1 = world.movePlayer(player, -1, 0);
        MoveResult result2 = world.movePlayer(player, -1, 0);
        MoveResult result3 = world.movePlayer(player, -1, 0);

        assertEquals(MoveResult.VALID, result1);
        assertEquals(MoveResult.INVALID, result3);
    }

    @Test
    public void testMovePlayer_GameCompleted() {
        world.setTileType(3, 2, 2);

        MoveResult result = world.movePlayer(player, 0, 1);

        assertEquals(MoveResult.GAME_COMPLETED, result);
        assertEquals(2, player.getX());
        assertEquals(3, player.getY());
    }

    @Test
    public void testGetMonstersAt() {
        Monster m1 = new Monster("Orc", 1, 1,2);
        Monster m2 = new Monster("Goblin", 1, 1,2);
        world.addEntity(m1);
        world.addEntity(m2);

        List<Monster> monsters = world.getMonstersAt(1, 1);

        assertEquals(2, monsters.size());
        assertTrue(monsters.contains(m1));
        assertTrue(monsters.contains(m2));
    }

    @Test
    public void testRemoveEntity() {
        Monster m = new Monster("Orc", 1, 1,2);
        world.addEntity(m);

        assertEquals(1, world.getMonstersAt(1, 1).size());

        world.removeEntity(m);

        assertEquals(0, world.getMonstersAt(1, 1).size());
    }
}