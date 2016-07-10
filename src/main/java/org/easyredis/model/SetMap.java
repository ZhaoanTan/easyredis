package org.easyredis.model;
/**
 * Created by Zhaoan.Tan on 2016/7/10.
 */

import org.easyredis.adapter.ISetMapAdapter;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class SetMap<
        EntityType,
        KeyType, KeyGetFun extends Function<EntityType, KeyType>, KeyToStringFun extends Function<KeyType, String>, StringToKeyFun extends Function<String, KeyType>,
        MemberType, MemberGetFun extends Function<EntityType, MemberType>, MemberToStringFun extends Function<MemberType, String>, StringToMemberFun extends Function<String, MemberType>,
        DataSrcType, DataSrcFun extends Function<DataSrcType, Collection<EntityType>>
        > {
    private String mapName;
    private ISetMapAdapter setMapAdapter;
    private Class<EntityType> entityType;
    private Class<KeyType> keyType;
    private KeyGetFun keyGetFun;
    private KeyToStringFun keyToStringFun;
    private StringToKeyFun stringToKeyFun;
    private Class<MemberType> memberType;
    private MemberGetFun memberGetFun;
    private MemberToStringFun memberToStringFun;
    private StringToMemberFun stringToMemberFun;
    private DataSrcType dataSrc;
    private DataSrcFun dataSrcFun;

    public SetMap(String mapName,
                  ISetMapAdapter setMapAdapter,
                  Class<EntityType> entityType,
                  Class<KeyType> keyType, KeyGetFun keyGetFun, KeyToStringFun keyToStringFun, StringToKeyFun stringToKeyFun,
                  Class<MemberType> memberType, MemberGetFun memberGetFun, MemberToStringFun memberToStringFun, StringToMemberFun stringToMemberFun, DataSrcType dataSrc, DataSrcFun dataSrcFun) {
        this.mapName = mapName;
        this.setMapAdapter = setMapAdapter;
        this.entityType = entityType;
        this.keyType = keyType;
        this.keyGetFun = keyGetFun;
        this.keyToStringFun = keyToStringFun;
        this.stringToKeyFun = stringToKeyFun;
        this.memberType = memberType;
        this.memberGetFun = memberGetFun;
        this.memberToStringFun = memberToStringFun;
        this.stringToMemberFun = stringToMemberFun;
        this.dataSrc = dataSrc;
        this.dataSrcFun = dataSrcFun;
        buildInMem();
    }

    void buildInMem() {
        Collection<EntityType> data = dataSrcFun.apply(dataSrc);
        java.util.Map<String, Set<String>> setMap = data.stream().collect(Collectors.groupingBy(e -> keyToStringFun.apply(keyGetFun.apply(e)), Collectors.mapping(e -> memberToStringFun.apply(memberGetFun.apply(e)), Collectors.toSet())));
        setMapAdapter.putInMem(mapName, setMap);
    }

    public Set<KeyType> keySet() {
        Set<String> keyStr = setMapAdapter.keySet(mapName);
        return keyStr.stream().map(stringToKeyFun).collect(Collectors.toSet());
    }

    public List<Set<MemberType>> values() {
        List<Set<String>> valueStr = setMapAdapter.values(mapName);
        return valueStr.stream().map(
                s -> {
                    return s.stream().map(stringToMemberFun).collect(Collectors.toSet());
                }
        ).collect(Collectors.toList());
    }

    public Set<MemberType> get(KeyType key) {
        Set<String> keyStrSet = setMapAdapter.get(mapName, keyToStringFun.apply(key));
        return keyStrSet.stream().map(stringToMemberFun).collect(Collectors.toSet());
    }

    public void put(KeyType key, Set<MemberType> value) {
        setMapAdapter.put(mapName, keyToStringFun.apply(key),
                value.stream().map(memberToStringFun).collect(Collectors.toSet()));
    }
}
