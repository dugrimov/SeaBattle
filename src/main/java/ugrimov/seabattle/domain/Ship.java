package ugrimov.seabattle.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.Collection;

import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Singular;
import lombok.Value;

@Value // The Ship is immutable now!
@Builder
@RequiredArgsConstructor
@JsonDeserialize(builder = Ship.ShipBuilder.class)
public class Ship {

    @NonNull @Singular
    private final Collection<String> cells;
    @NonNull @Singular
    private final Collection<String> hitCells;

    public boolean isKilled() {
        return hitCells.containsAll(cells);
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class ShipBuilder {
    }
  
}
