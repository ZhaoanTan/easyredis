package test;

import org.easyredis.adapter.factory.AdapterFactory;
import org.easyredis.model.SetMap;

import java.util.Collection;
import java.util.function.Function;

/**
 * Created by Zhaoan.Tan on 2016/7/10.
 */
public class Sex2IdSetMap extends SetMap<
        DataEntity,
        SexType, Function<DataEntity, SexType>, Function<SexType, String>, Function<String, SexType>,
        String, Function<DataEntity, String>, Function<String, String>, Function<String, String>,
        DataProvider, Function<DataProvider, Collection<DataEntity>>
        > {
    public Sex2IdSetMap(String mapName, DataProvider dataProvider) {
        super(mapName, AdapterFactory.setMapAdapter(),
                DataEntity.class,
                SexType.class, DataEntity::getSex, SexType::toString, SexType::valueOf,
                String.class, DataEntity::getId, Function.<String>identity(), Function.<String>identity(),
                dataProvider, DataProvider::provide);
    }
}
