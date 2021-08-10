package eu.sequence.tick;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public class TickEvent {

    private int ticksSinceLastSecond;
    private long time;


}
