package ugrimov.seabattle.players;

import org.apache.commons.lang3.StringUtils;
import ugrimov.seabattle.domain.Battlefield;

import java.util.Random;

public class RobotPlayer implements Player {

    @Override
    public String getCell(Battlefield enemyField) {
        var cell = StringUtils.EMPTY;
        do {
            cell = conceiveACell();
        } while (enemyField.getHits().contains(cell));
//        try {
//            Thread.sleep(200);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        return cell;
    }

    // protected for mocking
    protected String conceiveACell() {
        var random = new Random();
        var row = Battlefield.ROWS.get(random.nextInt(Battlefield.ROWS.size()));
        var column = Battlefield.COLUMNS.get(random.nextInt(Battlefield.COLUMNS.size()));
        return row + column;
    }
}
