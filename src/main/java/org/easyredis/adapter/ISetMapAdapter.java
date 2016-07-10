package org.easyredis.adapter;
/**
 * Created by Zhaoan.Tan on 2016/7/10.
 */

import java.util.List;
import java.util.Set;

public interface ISetMapAdapter {
    void putInMem(String map, java.util.Map<String, Set<String>> setMap);

    Set<String> get(String map, String key);

    long put(String map, String key, Set<String> set);

    long putIfAbsent(String map, String key, Set<String> set);

    long putAll(String map, java.util.Map<String, Set<String>> sets);

    long remove(String map, String key);

    Set<String> keySet(String map);

    List<Set<String>> values(String map);

    Set<String> membersAllSets(String map);

    long addMemberToSet(String map, String key, Set<String> set);

    long removeMemberFromSet(String map, String key);
}
