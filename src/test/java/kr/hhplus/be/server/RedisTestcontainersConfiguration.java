package kr.hhplus.be.server;

import com.redis.testcontainers.RedisContainer;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.utility.DockerImageName;

@Configuration
public class RedisTestcontainersConfiguration {

    public static final RedisContainer REDIS_CONTAINER;
    static {
        REDIS_CONTAINER = new RedisContainer(DockerImageName.parse("redis:7.4.3"))
                .withExposedPorts(6379);

        REDIS_CONTAINER.start();
        System.setProperty("spring.data.redis.host", REDIS_CONTAINER.getHost());
        System.setProperty("spring.data.redis.port", REDIS_CONTAINER.getMappedPort(6379).toString());
    }

    @PreDestroy
    public void preDestroy() {
        if (REDIS_CONTAINER.isRunning()) {
            REDIS_CONTAINER.stop();
        }
    }
}
