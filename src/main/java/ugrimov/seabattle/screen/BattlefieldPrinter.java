package ugrimov.seabattle.screen;

import java.util.Collection;
import java.util.List;
import ugrimov.seabattle.domain.Battlefield;

public class BattlefieldPrinter {
    
    private final static String NEW_LINE = System.getProperty("line.separator");
    
    public String print(Battlefield battlefield, BattlefieldCellMapper mapper) {
        var sb = new StringBuilder();
        sb.append(Battlefield.COLUMNS.stream().reduce(" ", (s1, s2) -> s1 + s2));
        sb.append(NEW_LINE);
        Battlefield.ROWS.forEach(row -> {
            sb.append(row);
            Battlefield.COLUMNS.forEach(column -> {
                sb.append(mapper.map(battlefield, row + column));
            });
            sb.append(NEW_LINE);
        });
        return sb.toString();
    }
    
}
