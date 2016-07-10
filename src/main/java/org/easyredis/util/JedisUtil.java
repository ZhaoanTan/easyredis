package org.easyredis.util;
/**
 * Created by Zhaoan.Tan on 2016/7/10.
 */

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisUtil {
    private static JedisPool jedisPool;

    public static JedisPool jedisPool() {
        if (jedisPool != null) {
            return jedisPool;
        }
        JedisPoolConfig config = jedisPoolConfig();
        jedisPool = new JedisPool(config, "127.0.0.1", 6379);
        return jedisPool;
    }

    public static JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        return config;
    }
}
