package ugrimov.seabattle.screen;

import ugrimov.seabattle.domain.Battlefield;

public abstract class BattlefieldCellMapper {
    
    static final String EMPTY_CELL = "·";
    static final String HIT_CELL = "*";
    static final String PLAYER_SHIP_CELL = "█";
    static final String HIT_SHIP_CELL = "▓";
    static final String KILLED_SHIP_CELL = "░";
    
    public String map(Battlefield battlefield, String cell) {
        for(var ship : battlefield.getShips()) {
            if (ship.getHitCells().contains(cell)) {
                return ship.isKilled() ? KILLED_SHIP_CELL : HIT_SHIP_CELL;
            } else if (ship.getCells().contains(cell)) {
                return getShipCell();
            }
        }
        return battlefield.getHits().contains(cell) ? HIT_CELL : EMPTY_CELL;
    }
    
    protected abstract String getShipCell();
}
