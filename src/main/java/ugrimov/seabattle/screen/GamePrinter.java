package ugrimov.seabattle.screen;

import ugrimov.seabattle.domain.Battlefield;

public class GamePrinter {
    private final BattlefieldPrinter battlefieldPrinter;

    public GamePrinter(BattlefieldPrinter battlefieldPrinter) {
        this.battlefieldPrinter = battlefieldPrinter;
    }

    public String print(Battlefield field1, BattlefieldCellMapper mapper1, Battlefield field2, BattlefieldCellMapper mapper2) {
        var screen1 = battlefieldPrinter.print(field1, mapper1).split(System.lineSeparator());
        var screen2 = battlefieldPrinter.print(field2, mapper2).split(System.lineSeparator());
        var sb = new StringBuilder();
        for (var i = 0; i < screen1.length; i++) {
            sb.append(String.format("%s\t%s", screen1[i], screen2[i]));
            sb.append(System.lineSeparator());
        }
        sb.append(String.format("Player1 hits: %s.", String.join(",", field2.getHits())));
        sb.append(System.lineSeparator());
        sb.append(String.format("Player2 hits: %s.", String.join(",", field1.getHits())));
        return sb.toString();
    }
}
