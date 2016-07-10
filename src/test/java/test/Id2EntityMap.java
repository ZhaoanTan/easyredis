package test;

import com.alibaba.fastjson.JSONObject;
import org.easyredis.adapter.factory.AdapterFactory;
import org.easyredis.model.Map;

import java.util.Collection;
import java.util.function.Function;

/**
 * Created by Zhaoan.Tan on 2016/7/10.
 */
public class Id2EntityMap extends Map<
        DataEntity,
        String, Function<DataEntity, String>, Function<String, String>, Function<String, String>,
        DataEntity, Function<DataEntity, DataEntity>, Function<DataEntity, String>, Function<String, DataEntity>,
        DataProvider, Function<DataProvider, Collection<DataEntity>>
        > {
    public Id2EntityMap(String mapName, DataProvider dataProvider) {
        super(mapName, AdapterFactory.mapAdapter(), DataEntity.class,
                String.class, DataEntity::getId, Function.<String>identity(), Function.<String>identity(),
                DataEntity.class, Function.<DataEntity>identity(), JSONObject::toJSONString, Id2EntityMap::stringToEntity,
                dataProvider, DataProvider::provide);
    }

    private static DataEntity stringToEntity(String json) {
        return JSONObject.parseObject(json, DataEntity.class);
    }

}
