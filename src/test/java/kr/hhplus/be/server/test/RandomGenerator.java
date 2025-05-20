package kr.hhplus.be.server.test;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.BuilderArbitraryIntrospector;
import com.navercorp.fixturemonkey.api.introspector.FailoverIntrospector;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import lombok.Getter;

import java.util.List;
import java.util.Random;

public final class RandomGenerator {

    private static final Random RANDOM = new Random();

    @Getter
    private static final FixtureMonkey fixtureMonkey = FixtureMonkey.builder()
            .objectIntrospector(new FailoverIntrospector(List.of(
                    FieldReflectionArbitraryIntrospector.INSTANCE,
                    BuilderArbitraryIntrospector.INSTANCE
            )))
            .defaultNotNull(true)
            .enableLoggingFail(false)
            .build();
}
