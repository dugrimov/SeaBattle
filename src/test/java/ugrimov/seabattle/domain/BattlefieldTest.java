package ugrimov.seabattle.domain;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;


class BattlefieldTest {

    @Test
    void testHit() {
        var ship1 = Ship.builder().cells(List.of("a1", "a2", "a3")).build();
        var ship2 = Ship.builder().cells(List.of("e5", "f5", "g5")).build();
        var battlefield1 = Battlefield.builder().ship(ship1).ship(ship2).build();

        assertTrue(battlefield1.getHits().isEmpty());
        assertTrue(ship1.getHitCells().isEmpty());
        assertTrue(ship2.getHitCells().isEmpty());

        final String cellA1 = "a1";
        var battlefield2 = battlefield1.hit(cellA1);
        assertEquals(1, battlefield2.getHits().size());
        assertTrue(battlefield2.getHits().contains(cellA1));

        BiFunction<Battlefield, String, List<Ship>> findHitShips =
                (battlefield, cell) -> battlefield.getShips().stream()
                .filter(ship -> ship.getCells().contains(cell)
                        && ship.getHitCells().contains(cell)
                        && ship.getHitCells().size() == 1)
                .collect(Collectors.toList());
        var hitShips = findHitShips.apply(battlefield2, cellA1);
        assertEquals(1, hitShips.size());
        // the same hit
        var battlefield3 = battlefield2.hit(cellA1);
        assertEquals(1, battlefield3.getHits().size());
        hitShips = findHitShips.apply(battlefield2, cellA1);
        assertEquals(1, hitShips.size());

        var cellB1 = "b1";
        var battlefield4 = battlefield3.hit(cellB1);
        assertEquals(2, battlefield4.getHits().size());
        assertTrue(battlefield4.getHits().contains(cellB1));

        BiFunction<Battlefield, String, List<Ship>> findHitShips2 =
                (battlefield, cell) -> battlefield.getShips().stream()
                        .filter(ship -> ship.getHitCells().contains(cell))
                        .collect(Collectors.toList());
        assertTrue(findHitShips2.apply(battlefield4, cellB1).isEmpty());
        // the same hit
        var battlefield5 = battlefield4.hit(cellB1);
        assertEquals(2, battlefield5.getHits().size());
        assertTrue(findHitShips2.apply(battlefield5, cellB1).isEmpty());
    }

    @Test
    void testIsLastHitSuccessful() {
        var ship = Ship.builder().cells(List.of("a1", "a2", "a3")).build();
        var battlefield = Battlefield.builder().ship(ship).build();
        assertFalse(battlefield.isLastHitSuccessful());

        ship = Ship.builder().cells(List.of("a1", "a2", "a3")).hitCell("a2").build();
        battlefield = Battlefield.builder().ship(ship).hit("a2").build();
        assertTrue(battlefield.isLastHitSuccessful());

        battlefield = Battlefield.builder().ship(ship).hit("a2").hit("b1").build();
        assertFalse(battlefield.isLastHitSuccessful());
    }

    @Test
    void testIsDefeated() {
        var ship1 = Ship.builder().cells(List.of("a1", "a2")).build();
        var ship2 = Ship.builder().cell("a10").build();
        assertFalse(Battlefield.builder().ships(List.of(ship1, ship2)).build().isDefeated());

        ship1 = Ship.builder().cells(List.of("a1", "a2")).build();
        ship2 = Ship.builder().cell("a10").hitCell("a10").build();
        assertFalse(Battlefield.builder().ships(List.of(ship1, ship2)).build().isDefeated());

        ship1 = Ship.builder().cells(List.of("a1", "a2")).hitCell("a1").build();
        ship2 = Ship.builder().cell("a10").hitCell("a10").build();
        assertFalse(Battlefield.builder().ships(List.of(ship1, ship2)).build().isDefeated());

        ship1 = Ship.builder().cells(List.of("a1", "a2")).hitCells(List.of("a1", "a2")).build();
        ship2 = Ship.builder().cell("a10").hitCell("a10").build();
        assertTrue(Battlefield.builder().ships(List.of(ship1, ship2)).build().isDefeated());
    }
}
