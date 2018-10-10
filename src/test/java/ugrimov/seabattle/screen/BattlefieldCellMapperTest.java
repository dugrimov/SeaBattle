package ugrimov.seabattle.screen;

import java.util.Collection;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import ugrimov.seabattle.domain.Battlefield;
import ugrimov.seabattle.domain.Ship;

public class BattlefieldCellMapperTest {

    private final Ship ship1 = new Ship(List.of("a1", "a2"), List.of());
    private final Ship ship2 = new Ship(List.of("b1", "b2"), List.of("b1"));
    private final Ship ship3 = new Ship(List.of("c1", "c2"), List.of("c2", "c1"));
    private final Collection<String> hits = List.of("b1", "b2", "c2", "c1", "d1");
    private final Battlefield battlefield = new Battlefield(List.of(ship1, ship2, ship3), hits);

    @Test
    public void testPlayerMap() {
        var mapper = new PlayerBattlefieldCellMapper();
        testBaseMap(mapper);
        assertEquals(BattlefieldCellMapper.PLAYER_SHIP_CELL, mapper.map(battlefield, "a1"));
        assertEquals(BattlefieldCellMapper.PLAYER_SHIP_CELL, mapper.map(battlefield, "a2"));
    }

    @Test
    public void testRobotMap() {
        var mapper = new RobotBattlefieldCellMapper();
        testBaseMap(mapper);
        assertEquals(BattlefieldCellMapper.EMPTY_CELL, mapper.map(battlefield, "a1"));
        assertEquals(BattlefieldCellMapper.EMPTY_CELL, mapper.map(battlefield, "a2"));
    }

    private void testBaseMap(BattlefieldCellMapper mapper) {
        assertEquals(BattlefieldCellMapper.EMPTY_CELL, mapper.map(battlefield, "a3"));
        assertEquals(BattlefieldCellMapper.KILLED_SHIP_CELL, mapper.map(battlefield, "c1"));
        assertEquals(BattlefieldCellMapper.KILLED_SHIP_CELL, mapper.map(battlefield, "c2"));
        assertEquals(BattlefieldCellMapper.HIT_SHIP_CELL, mapper.map(battlefield, "b1"));
        assertEquals(BattlefieldCellMapper.HIT_CELL, mapper.map(battlefield, "d1"));
    }

}
