package ugrimov.seabattle.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.Collection;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
@JsonDeserialize(builder = Ship.ShipBuilder.class)
public class Ship {

    @NonNull
    private final Collection<String> cells;
    @NonNull
    private final Collection<String> hitCells;

    public boolean isKilled() {
        return hitCells.containsAll(cells);
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class ShipBuilder {
    }
  
}
