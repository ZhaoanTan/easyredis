package org.easyredis.adapter;
/**
 * Created by Zhaoan.Tan on 2016/7/10.
 */

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RedisMapAdapter implements IMapAdapter {
    private JedisPool jedisPool;

    public RedisMapAdapter(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    private Jedis getJedis() {
        return jedisPool.getResource();
    }

    public void putInMem(String mapName, java.util.Map<String, String> map) {
        getJedis().hmset(mapName, map);
    }

    public String get(String map, String key) {
        if (map == null || map.isEmpty() || key == null || key.isEmpty()) {
            return null;
        }
        return getJedis().hget(map, key);
    }

    public List<String> get(String map, String... keys) {
        if (keys == null || keys.length == 0) {
            return null;
        } else if (keys.length == 1) {
            if (keys[0] == null || keys[0].isEmpty()) {
                return null;
            } else {
                List<String> ls = new ArrayList<>();
                ls.add(getJedis().hget(map, keys[0]));
                return ls;
            }
        } else {
            return getJedis().hmget(map, keys);
        }
    }

    public List<String> get(String map, Set<String> keys) {
        if (keys == null || keys.size() == 0) {
            return null;
        }
        return get(map, keys.toArray(new String[keys.size()]));
    }

    public long put(String mapName, String key, String value) {
        return getJedis().hset(mapName, key, value);
    }

    public String put(String map, java.util.Map<String, String> keyValue) {
        return getJedis().hmset(map, keyValue);
    }

    public long putIfAbsent(String map, String key, String value) {
        return getJedis().hsetnx(map, key, value);
    }

    public long remove(String map, String... key) {
        if (key == null || key.length == 0) {
            return 0;
        }
        return getJedis().hdel(map, key);
    }

    public Set<String> keySet(String mapName) {
        return getJedis().hkeys(mapName);
    }

    public List<String> values(String mapName) {
        return getJedis().hvals(mapName);
    }

    public Map<String, String> pairs(String mapName) {
        return getJedis().hgetAll(mapName);
    }

}
