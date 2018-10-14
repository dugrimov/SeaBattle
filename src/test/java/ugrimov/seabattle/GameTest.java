package ugrimov.seabattle;

import org.junit.jupiter.api.Test;
import ugrimov.seabattle.domain.Battlefield;
import ugrimov.seabattle.domain.Ship;
import ugrimov.seabattle.players.Player;
import ugrimov.seabattle.screen.BattlefieldCellMapper;
import ugrimov.seabattle.screen.GamePrinter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class GameTest {

    @Test
    void testStart() throws IOException {
        var battlefield1 = Battlefield.builder().ship(Ship.builder().cell("b1").cell("d1").build()).build();
        var battlefield2 = Battlefield.builder().ship(Ship.builder().cell("a2").cell("a3").build()).build();
        var player1Mock = mock(Player.class, "player1");
        when(player1Mock.getCell(any())).thenReturn("a1", "a2", "a3");
        var player2Mock = mock(Player.class, "player2");
        when(player2Mock.getCell(any())).thenReturn("b1", "b2");

        var gamePrinterMock = mock(GamePrinter.class);
        var mapper1Mock = mock(BattlefieldCellMapper.class);
        var mapper2Mock = mock(BattlefieldCellMapper.class);

        try (var baos = new ByteArrayOutputStream(); var printStream = new PrintStream(baos, true,
                StandardCharsets.UTF_8)) {
            var game = new Game();
            game.start(gamePrinterMock, player1Mock, battlefield1, mapper1Mock, player2Mock, battlefield2,
                    mapper2Mock, printStream);

            var data = new String(baos.toByteArray(), StandardCharsets.UTF_8);
            assertTrue(data.endsWith("Game over. Player1 has won."));
        }

        var playersInOrder = inOrder(player1Mock, player2Mock);
        playersInOrder.verify(player1Mock).getCell(any());
        playersInOrder.verify(player2Mock, times(2)).getCell(any());
        playersInOrder.verify(player1Mock, times(2)).getCell(any());
    }
}