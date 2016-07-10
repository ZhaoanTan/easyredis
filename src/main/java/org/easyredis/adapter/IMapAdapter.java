package org.easyredis.adapter;
/**
 * Created by Zhaoan.Tan on 2016/7/10.
 */

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IMapAdapter {
    void putInMem(String mapName, java.util.Map<String, String> map);

    String get(String map, String key);

    List<String> get(String map, String... keys);

    List<String> get(String map, Set<String> keys);

    long put(String mapName, String key, String value);

    String put(String map, java.util.Map<String, String> keyValue);

    long putIfAbsent(String map, String key, String value);

    long remove(String map, String... key);

    Set<String> keySet(String mapName);

    List<String> values(String mapName);

    Map<String, String> pairs(String mapName);
}
