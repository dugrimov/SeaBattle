package ugrimov.seabattle;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.PrintStream;

import ugrimov.seabattle.domain.Battlefield;
import ugrimov.seabattle.players.HumanPlayer;
import ugrimov.seabattle.players.Player;
import ugrimov.seabattle.players.PlayerSelector;
import ugrimov.seabattle.players.RobotPlayer;
import ugrimov.seabattle.screen.*;

public class Game {

    public static void main(String[] args) throws IOException {
        startRobotsGame();
    }

    public static void startGameWithAHuman() throws IOException {
        Battlefield humanField = new ObjectMapper().readValue(Game.class.getResourceAsStream("playerfield.json"),
                Battlefield.class);
        Battlefield robotField = new ObjectMapper().readValue(Game.class.getResourceAsStream("robotfield.json"),
                Battlefield.class);
        var playerMapper = new PlayerBattlefieldCellMapper();
        var robotMapper = new RobotBattlefieldCellMapper();
        var gamePrinter = new GamePrinter(new BattlefieldPrinter());
        var game = new Game();
        game.start(gamePrinter, new HumanPlayer(), humanField, playerMapper, new RobotPlayer(), robotField,
                robotMapper, System.out);
    }

    public static void startRobotsGame() throws IOException {
        Battlefield humanField = new ObjectMapper().readValue(Game.class.getResourceAsStream("playerfield.json"),
                Battlefield.class);
        Battlefield robotField = new ObjectMapper().readValue(Game.class.getResourceAsStream("robotfield.json"),
                Battlefield.class);
        var robotMapper = new RobotBattlefieldCellMapper();
        var gamePrinter = new GamePrinter(new BattlefieldPrinter());
        var game = new Game();
        game.start(gamePrinter, new RobotPlayer(), humanField, robotMapper, new RobotPlayer(), robotField,
                robotMapper, System.out);
    }

    public void start(GamePrinter gamePrinter, Player player1, Battlefield field1, BattlefieldCellMapper mapper1,
                      Player player2, Battlefield field2, BattlefieldCellMapper mapper2, PrintStream out) {
        out.println(gamePrinter.print(field1, mapper1, field2, mapper2));
        var hasHit = true;
        Player currentPlayer;
        Battlefield enemyField;
        var playerSelector = new PlayerSelector(player1, player2);
        for (var i = 0; i < 10000; i++) {
            currentPlayer = playerSelector.next(hasHit);
            enemyField = currentPlayer == player1 ? field2 : field1;
            hasHit = enemyField.hit(currentPlayer.getCell(enemyField));

            out.println(gamePrinter.print(field1, mapper1, field2, mapper2));

            if (field1.isDefeated() || field2.isDefeated()) {
                break;
            }
        }
        out.print(String.format("Game over. Player%d has won.", field1.isDefeated() ? 2 : 1));
    }
}
