package ugrimov.seabattle.domain;

import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShipTest {

    @Test
    void testImmutability() {
        var ship1 = Ship.builder().cell("a1").hitCell("b1").build();
        assertNotNull(ship1);
        var ship2 = Ship.builder().cells(ship1.getCells()).hitCells(ship1.getHitCells()).hitCell("b2").build();
        assertNotNull(ship2);
        assertNotSame(ship1, ship2);
        assertNotSame(ship1.getCells(), ship2.getCells());
        assertIterableEquals(ship1.getCells(), ship2.getCells());
        assertNotSame(ship1.getHitCells(), ship2.getHitCells());
        assertIterableEquals(List.of("b1", "b2"), ship2.getHitCells());
    }

    @Test
    void testIsKilled() {
        assertFalse(Ship.builder().cell("a1").build().isKilled());
        assertFalse(Ship.builder().cells(List.of("a1", "a2")).hitCell("a1").build().isKilled());
        assertFalse(Ship.builder().cells(List.of("a1", "a2")).hitCell("a2").build().isKilled());
        assertTrue(Ship.builder().cell("a1").hitCell("a1").build().isKilled());
        assertTrue(Ship.builder().cells(List.of("a1", "a2")).hitCells(List.of("a1", "a2")).build().isKilled());
        assertTrue(Ship.builder().cells(List.of("a1", "a2")).hitCells(List.of("a2", "a1")).build().isKilled());
    }
    
}
