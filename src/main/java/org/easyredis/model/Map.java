package org.easyredis.model;
/**
 * Created by Zhaoan.Tan on 2016/7/10.
 */

import org.easyredis.adapter.IMapAdapter;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class Map<
        EntityType,
        KeyType, KeyGetFun extends Function<EntityType, KeyType>, KeyToStringFun extends Function<KeyType, String>, StringToKeyFun extends Function<String, KeyType>,
        ValueType, ValueGetFun extends Function<EntityType, ValueType>, ValueToStringFun extends Function<ValueType, String>, StringToValueFun extends Function<String, ValueType>,
        DataSrcType, DataSrcFun extends Function<DataSrcType, Collection<EntityType>>
        > {
    private String mapName;
    private IMapAdapter mapAdapter;
    private Class<EntityType> entityType;
    private Class<KeyType> keyType;
    private KeyGetFun keyGetFun;
    private KeyToStringFun keyToStringFun;
    private StringToKeyFun stringToKeyFun;
    private Class<ValueType> valueType;
    private ValueGetFun valueGetFun;
    private ValueToStringFun valueToStringFun;
    private StringToValueFun stringToValueFun;
    private DataSrcType dataSrc;
    private DataSrcFun dataSrcFun;

    public Map(String mapName,
               IMapAdapter mapAdapter,
               Class<EntityType> entityType,
               Class<KeyType> keyType, KeyGetFun keyGetFun, KeyToStringFun keyToStringFun, StringToKeyFun stringToKeyFun,
               Class<ValueType> valueType, ValueGetFun valueGetFun, ValueToStringFun valueToStringFun, StringToValueFun stringToValueFun,
               DataSrcType dataSrc, DataSrcFun dataSrcFun) {
        this.mapName = mapName;
        this.mapAdapter = mapAdapter;
        this.entityType = entityType;
        this.keyType = keyType;
        this.keyGetFun = keyGetFun;
        this.keyToStringFun = keyToStringFun;
        this.stringToKeyFun = stringToKeyFun;
        this.valueType = valueType;
        this.valueGetFun = valueGetFun;
        this.valueToStringFun = valueToStringFun;
        this.stringToValueFun = stringToValueFun;
        this.dataSrc = dataSrc;
        this.dataSrcFun = dataSrcFun;
        buildInMem();
    }

    void buildInMem() {
        Collection<EntityType> data = dataSrcFun.apply(dataSrc);
        java.util.Map<String, String> map = data.stream().collect(Collectors.toMap(e -> keyToStringFun.apply(keyGetFun.apply(e)), e -> valueToStringFun.apply(valueGetFun.apply(e))));
        mapAdapter.putInMem(mapName, map);
    }

    public ValueType get(KeyType key) {
        String res = mapAdapter.get(mapName, keyToStringFun.apply(key));
        return stringToValueFun.apply(res);
    }

    public long put(KeyType key, ValueType value) {
        return mapAdapter.put(mapName, keyToStringFun.apply(key), valueToStringFun.apply(value));
    }

    public Set<KeyType> keySet() {
        Set<String> set = mapAdapter.keySet(mapName);
        return set.stream().map(stringToKeyFun).collect(Collectors.toSet());
    }

    public List<ValueType> values() {
        List<String> ls = mapAdapter.values(mapName);
        return ls.stream().map(stringToValueFun).collect(Collectors.toList());
    }
}
