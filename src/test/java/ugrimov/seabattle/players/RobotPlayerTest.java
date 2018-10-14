package ugrimov.seabattle.players;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import ugrimov.seabattle.domain.Battlefield;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

class RobotPlayerTest {

    @Test
    void testGetCell() {
//        var battlefield = mock(Battlefield.class);
//        when(battlefield.getHits()).thenReturn(List.of(hitCell));
        var hitCell = "a1";
        var battlefield = Battlefield.builder().hit(hitCell).build();
        var player = spy(new RobotPlayer());
        var mishitCell = "a2";
        when(player.conceiveACell()).thenReturn(hitCell).thenReturn(mishitCell);

        assertEquals(mishitCell, player.getCell(battlefield));
//        verify(battlefield, times(2)).getHits();
    }

    @Test
    void testConceiveACell() {
        var cell = new RobotPlayer().conceiveACell();
        assertTrue(StringUtils.isNotBlank(cell));
        assertTrue((int)cell.charAt(0) >= 97 && (int)cell.charAt(0) <= 106);
        var row = Integer.valueOf(cell.substring(1));
        assertTrue(row >= 1 && row <= 10);
    }
}