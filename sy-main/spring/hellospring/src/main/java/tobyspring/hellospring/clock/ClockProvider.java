package tobyspring.hellospring.clock;

import org.springframework.stereotype.Component;

import java.time.Clock;

@Component
public class ClockProvider {

    private final Clock clock;

    public ClockProvider(Clock clock) {
        this.clock = clock;
    }

    public Clock clock() {
        return clock;
    }
}
