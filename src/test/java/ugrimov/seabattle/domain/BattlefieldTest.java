package ugrimov.seabattle.domain;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;


public class BattlefieldTest {

    @Test
    public void testHit() {
        var ship1 = new Ship(List.of("a1", "a2", "a3"), new ArrayList<String>());
        var ship2 = new Ship(List.of("e5", "f5", "g5"), new ArrayList<String>());
        var battlefield = new Battlefield(List.of(ship1, ship2), new ArrayList<String>());

        assertTrue(battlefield.getHits().isEmpty());
        assertTrue(ship1.getHitCells().isEmpty());
        assertTrue(ship2.getHitCells().isEmpty());

        var cell = "a1";
        assertTrue(battlefield.hit(cell));
        assertEquals(1, battlefield.getHits().size());
        assertTrue(battlefield.getHits().contains(cell));
        assertEquals(1, ship1.getHitCells().size());
        assertTrue(ship1.getHitCells().contains(cell));
        assertEquals(0, ship2.getHitCells().size());
        // the same hit
        assertFalse(battlefield.hit(cell));
        assertEquals(1, battlefield.getHits().size());
        assertEquals(1, ship1.getHitCells().size());
        assertEquals(0, ship2.getHitCells().size());

        cell = "b1";
        assertFalse(battlefield.hit(cell));
        assertEquals(2, battlefield.getHits().size());
        assertTrue(battlefield.getHits().contains(cell));
        assertEquals(1, ship1.getHitCells().size());
        assertEquals(0, ship2.getHitCells().size());
        // the same hit
        assertFalse(battlefield.hit(cell));
        assertEquals(2, battlefield.getHits().size());
        assertEquals(1, ship1.getHitCells().size());
        assertEquals(0, ship2.getHitCells().size());
    }
    
    @Test
    public void testIsDefeated() {
        var ship1 = new Ship(List.of("a1", "a2"), new ArrayList());
        var ship2 = new Ship(List.of("a10"), new ArrayList());
        assertFalse(new Battlefield(List.of(ship1, ship2), new ArrayList()).isDefeated());
        
        ship1 = new Ship(List.of("a1", "a2"), new ArrayList());
        ship2 = new Ship(List.of("a10"), List.of("a10"));
        assertFalse(new Battlefield(List.of(ship1, ship2), new ArrayList()).isDefeated());
        
        ship1 = new Ship(List.of("a1", "a2"), List.of("a1"));
        ship2 = new Ship(List.of("a10"), List.of("a10"));
        assertFalse(new Battlefield(List.of(ship1, ship2), new ArrayList()).isDefeated());
        
        ship1 = new Ship(List.of("a1", "a2"), List.of("a1", "a2"));
        ship2 = new Ship(List.of("a10"), List.of("a10"));
        assertTrue(new Battlefield(List.of(ship1, ship2), new ArrayList()).isDefeated());
    }
}
