package org.easyredis.adapter;
/**
 * Created by Zhaoan.Tan on 2016/7/10.
 */

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RedisSetMapAdapter implements ISetMapAdapter {
    private JedisPool jedisPool;

    public RedisSetMapAdapter(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    private Jedis getJedis() {
        return jedisPool.getResource();
    }

    public void putInMem(String map, java.util.Map<String, Set<String>> setMap) {
        setMap.entrySet().stream().forEach(e -> getJedis().sadd(nameOfSetInRedis(map, e.getKey()), e.getValue().toArray(new String[e.getValue().size()])));
    }

    public Set<String> get(String map, String key) {
        return getJedis().smembers(nameOfSetInRedis(map, key));
    }

    public long put(String map, String key, Set<String> set) {
        String name = nameOfSetInRedis(map, key);
        getJedis().del(name);
        return getJedis().sadd(name, set.toArray(new String[set.size()]));
    }

    public long putIfAbsent(String map, String key, Set<String> set) {
        String name = nameOfSetInRedis(map, key);
        Set<String> setName = getJedis().keys(name);
        if (setName.size() > 0) {
            return 0;
        } else {
            return put(map, key, set);
        }
    }

    public long putAll(String map, java.util.Map<String, Set<String>> sets) {
        int count = 0;
        for (java.util.Map.Entry<String, Set<String>> set : sets.entrySet()) {
            count += put(map, set.getKey(), set.getValue());
        }
        return count;
    }

    public long remove(String map, String key) {
        String name = nameOfSetInRedis(map, key);
        return getJedis().del(name);
    }

    public Set<String> keySet(String map) {
        return getJedis().keys(nameOfSetInRedis(map, "*"));
    }

    public List<Set<String>> values(String map) {
        Set<String> setNames = getJedis().keys(nameOfSetInRedis(map, "*"));
        List<Set<String>> sets = new ArrayList<>();
        for (String name : setNames) {
            Set<String> set = getJedis().smembers(name);
            sets.add(set);
        }
        return sets;
    }

    public Set<String> membersAllSets(String map) {
        Set<String> setNames = getJedis().keys(nameOfSetInRedis(map, "*"));
        Set<String> setAll = new HashSet<>();
        for (String name : setNames) {
            Set<String> set = getJedis().smembers(name);
            setAll.addAll(set);
        }
        return setAll;
    }

    public long addMemberToSet(String map, String key, Set<String> set) {
        String name = nameOfSetInRedis(map, key);
        return getJedis().sadd(name, set.toArray(new String[set.size()]));
    }

    public long removeMemberFromSet(String map, String key) {
        String name = nameOfSetInRedis(map, key);
        return getJedis().srem(name);
    }

    private String nameOfSetInRedis(String map, String key) {
        return map + "_" + key;
    }
}
