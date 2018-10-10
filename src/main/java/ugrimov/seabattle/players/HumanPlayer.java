package ugrimov.seabattle.players;

import ugrimov.seabattle.domain.Battlefield;

public class HumanPlayer implements Player {

    @Override
    public String getCell(Battlefield enemyField) {
        return System.console().readLine();
    }

}
