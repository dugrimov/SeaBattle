package ugrimov.seabattle.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
@JsonDeserialize(builder = Battlefield.BattlefieldBuilder.class)
public class Battlefield {

    public final static List<String> ROWS = List.of("a", "b", "c", "d", "e", "f", "g", "h", "i", "j");
    public final static List<String> COLUMNS = List.of("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");

    @NonNull
    private final Collection<Ship> ships;

    @NonNull
    private final Collection<String> hits;

    public boolean hit(String cell) {
        var result = new AtomicBoolean(false);
        if (!hits.contains(cell)) {
            hits.add(cell);
            ships.forEach(ship -> {
                if (ship.getCells().contains(cell) && !ship.getHitCells().contains(cell)) {
                    ship.getHitCells().add(cell);
                    result.set(true);
                }
            });
        }
        return result.get();
    }

    public boolean isDefeated() {
        return ships.stream().allMatch(ship -> ship.getHitCells().containsAll(ship.getCells()));
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class BattlefieldBuilder {
    }   
    
}
