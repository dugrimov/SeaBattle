package ugrimov.seabattle.players;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import ugrimov.seabattle.domain.Battlefield;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class RobotPlayerTest {

    @Test
    void testGetCell() {
        var fieldMock = mock(Battlefield.class);
        var hitCell = "a1";
        var unhitCell = "a2";
        when(fieldMock.getHits()).thenReturn(List.of(hitCell));
        var player = spy(new RobotPlayer());
        when(player.conceiveACell()).thenReturn(hitCell).thenReturn(unhitCell);

        assertEquals(unhitCell, player.getCell(fieldMock));
        verify(fieldMock, times(2)).getHits();
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