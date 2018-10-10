package ugrimov.seabattle.players;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class PlayerSelectorTest {

    @Test
    void testNext() {
        var player1 = mock(Player.class);
        var player2 = mock(Player.class);
        var provider = new PlayerSelector(player1, player2);
        assertSame(player1, provider.next(true));
        assertSame(player2, provider.next(false));
        assertSame(player2, provider.next(true));
        assertSame(player2, provider.next(true));
        assertSame(player1, provider.next(false));
    }
}