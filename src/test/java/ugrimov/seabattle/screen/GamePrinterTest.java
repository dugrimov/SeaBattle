package ugrimov.seabattle.screen;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import ugrimov.seabattle.domain.Battlefield;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GamePrinterTest {

    @Test
    void testPrint() {
        var battlefield1 = Battlefield.builder().hit("a1").build();
        var battlefield2 = Battlefield.builder().hit("b3").hit("c10").build();
        var mapper1Mock = mock(BattlefieldCellMapper.class);
        var mapper2Mock = mock(BattlefieldCellMapper.class);

        var battlefieldPrinterMock = mock(BattlefieldPrinter.class);
        var field1 = String.format("fie%sld1", System.lineSeparator());
        var field2 = String.format("fie%sld2", System.lineSeparator());
        when(battlefieldPrinterMock.print(battlefield1, mapper1Mock)).thenReturn(field1);
        when(battlefieldPrinterMock.print(battlefield2, mapper2Mock)).thenReturn(field2);

        var gamePrinter = new GamePrinter(battlefieldPrinterMock);
        var actualGame = gamePrinter.print(battlefield1, mapper1Mock, battlefield2, mapper2Mock);
        assertTrue(StringUtils.isNotBlank(actualGame));
        var actualStrings = actualGame.split(System.lineSeparator());
        assertEquals(4, actualStrings.length);
        assertEquals("fie\tfie", actualStrings[0]);
        assertEquals("ld1\tld2", actualStrings[1]);
        assertEquals("Player1 hits: b3,c10.", actualStrings[2]);
        assertEquals("Player2 hits: a1.", actualStrings[3]);
    }
}