package ugrimov.seabattle.players;

public class PlayerSelector {

    private Player current;
    private final Player first;
    private final Player second;

    public PlayerSelector(Player first, Player second) {
        current = first;
        this.first = first;
        this.second = second;
    }

    public Player next(boolean hasHit) {
        if (!hasHit) {
            if (current == first) {
                current = second;
            } else {
                current = first;
            }
        }
        return current;
    }
}
