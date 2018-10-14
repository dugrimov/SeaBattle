package ugrimov.seabattle.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.*;

import java.util.Collection;
import java.util.List;

@Value
@Builder
@RequiredArgsConstructor
@JsonDeserialize(builder = Battlefield.BattlefieldBuilder.class)
public class Battlefield {

    public final static List<String> ROWS = List.of("a", "b", "c", "d", "e", "f", "g", "h", "i", "j");
    public final static List<String> COLUMNS = List.of("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");

    @NonNull
    @Singular
    private final Collection<Ship> ships;

    @NonNull
    @Singular
    private final List<String> hits;

    public Battlefield hit(String cell) {
        var builder = Battlefield.builder().hits(hits);
        if (!hits.contains(cell)) {
            builder.hit(cell);
        }
        ships.forEach(ship -> {
            var shipBuilder = Ship.builder().cells(ship.getCells()).hitCells(ship.getHitCells());
            if (ship.getCells().contains(cell) && !ship.getHitCells().contains(cell)) {
                shipBuilder.hitCell(cell);
            }
            builder.ship(shipBuilder.build());
        });
        return builder.build();
    }

    public boolean isLastHitSuccessful() {
        if (hits.size() == 0) {
            return false;
        }
        var lastHit = hits.get(hits.size() - 1);
        return ships.stream().anyMatch(ship -> ship.getHitCells().contains(lastHit));
    }

    public boolean isDefeated() {
        return ships.stream().allMatch(ship -> ship.getHitCells().containsAll(ship.getCells()));
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class BattlefieldBuilder {
    }

}
