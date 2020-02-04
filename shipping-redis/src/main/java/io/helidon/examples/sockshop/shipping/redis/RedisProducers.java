package io.helidon.examples.sockshop.shipping.redis;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import io.helidon.config.Config;
import io.helidon.examples.sockshop.shipping.Shipment;

import lombok.extern.java.Log;

import org.redisson.Redisson;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;

import static java.lang.String.format;

/**
 * CDI support for Redis/Redisson.
 */
@ApplicationScoped
@Log
public class RedisProducers {

    /**
     * Default Redis host to connect to.
     */
    public static final String DEFAULT_HOST = "shipping-db";

    /**
     * Default Redis port to connect to.
     */
    public static final int DEFAULT_PORT = 6379;

    /**
     * CDI Producer for {@code RedissonClient}.
     *
     * @param config application configuration, which will be used to read
     *               the values of a {@code db.host} and {@code db.port}
     *               configuration properties, if present. If either is not
     *               present, the defaults defined by the {@link #DEFAULT_HOST}
     *               and {@link #DEFAULT_PORT} constants will be used.
     *
     * @return a {@code RedissonClient} instance
     */
    @Produces
    @ApplicationScoped
    public static RedissonClient client(Config config) {
        Config db = config.get("db");
        return client(db.get("host").asString().orElse(DEFAULT_HOST),
                      db.get("port").asInt().orElse(DEFAULT_PORT));
    }

    /**
     * CDI Producer for the {@code RMap} that contains shipments.
     *
     * @param client the {@code RedissonClient} to use
     *
     * @return a {@code RMap} instance for the shipments
     */
    @Produces
    @ApplicationScoped
    public static RMap<String, Shipment> shipments(RedissonClient client) {
        return client.getMap("shipments");
    }

    // ---- helpers ---------------------------------------------------------

    /**
     * Create {@code RedissonClient} for the specified host and port.
     *
     * @param host the Redis host to connect to
     * @param port the Redis port to connect to
     *
     * @return a {@code RedissonClient} instance
     */
    @SuppressWarnings("Duplicates")
    static RedissonClient client(String host, int port) {
        log.info(format("Connecting to Redis on host %s:%d", host, port));

        org.redisson.config.Config config = new org.redisson.config.Config();
        config.useSingleServer()
                .setAddress(format("redis://%s:%d", host, port));

        return Redisson.create(config);
    }

}
