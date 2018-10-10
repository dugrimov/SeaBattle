package ugrimov.seabattle.domain;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class ShipTest {
    
    @Test
    public void testIsKilled() {
        assertFalse(new Ship(List.of("a1"), List.of()).isKilled());
        assertFalse(new Ship(List.of("a1", "a2"), List.of("a1")).isKilled());
        assertFalse(new Ship(List.of("a1", "a2"), List.of("a2")).isKilled());
        assertTrue(new Ship(List.of("a1"), List.of("a1")).isKilled());
        assertTrue(new Ship(List.of("a1", "a2"), List.of("a1", "a2")).isKilled());
        assertTrue(new Ship(List.of("a1", "a2"), List.of("a2", "a1")).isKilled());
    }
    
}
