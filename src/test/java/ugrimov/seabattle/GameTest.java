package ugrimov.seabattle;

import org.junit.jupiter.api.Test;
import ugrimov.seabattle.domain.Battlefield;
import ugrimov.seabattle.players.Player;
import ugrimov.seabattle.players.PlayerSelector;
import ugrimov.seabattle.screen.BattlefieldCellMapper;
import ugrimov.seabattle.screen.GamePrinter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameTest {

    @Test
    void testStart() throws IOException {
        var field1Mock = mock(Battlefield.class, "field1");
        when(field1Mock.isDefeated()).thenReturn(false, false, false, false, false);

        var field2Mock = mock(Battlefield.class, "field2");
        when(field2Mock.isDefeated()).thenReturn(false, false, false, false, true);

        var player1Mock = mock(Player.class, "player1");
        when(player1Mock.getCell(field2Mock)).thenReturn("a1", "a2", "a3");
        when(field2Mock.hit("a1")).thenReturn(false);
        when(field2Mock.hit("a2")).thenReturn(true);
        when(field2Mock.hit("a3")).thenReturn(true);
        var player2Mock = mock(Player.class, "player2");
        when(player2Mock.getCell(field1Mock)).thenReturn("b1", "b2");
        when(field1Mock.hit("b1")).thenReturn(true);
        when(field1Mock.hit("b2")).thenReturn(false);

        var gamePrinterMock = mock(GamePrinter.class);
        var mapper1Mock = mock(BattlefieldCellMapper.class);
        var mapper2Mock = mock(BattlefieldCellMapper.class);

        try (var baos = new ByteArrayOutputStream(); var printStream = new PrintStream(baos, true, StandardCharsets.UTF_8)) {
            var game = new Game();
            game.start(gamePrinterMock, player1Mock, field1Mock, mapper1Mock, player2Mock, field2Mock, mapper2Mock, printStream);

            var data = new String(baos.toByteArray(), StandardCharsets.UTF_8);
            assertTrue(data.endsWith("Game over. Player1 has won."));
        }

        var playersInOrder = inOrder(player1Mock, player2Mock);
        playersInOrder.verify(player1Mock).getCell(field2Mock);
        playersInOrder.verify(player2Mock, times(2)).getCell(field1Mock);
        playersInOrder.verify(player1Mock, times(2)).getCell(field2Mock);

        var fieldsInOrder = inOrder(field1Mock, field2Mock);
        fieldsInOrder.verify(field2Mock).hit("a1");
        fieldsInOrder.verify(field1Mock).hit("b1");
        fieldsInOrder.verify(field1Mock).hit("b2");
        fieldsInOrder.verify(field2Mock).hit("a2");
        fieldsInOrder.verify(field2Mock).hit("a3");

        verify(field1Mock, times(6)).isDefeated();
        verify(field2Mock, times(5)).isDefeated();
    }
}