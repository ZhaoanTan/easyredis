package org.easyredis.adapter.factory;
/**
 * Created by Zhaoan.Tan on 2016/7/10.
 */

import org.easyredis.adapter.IMapAdapter;
import org.easyredis.adapter.ISetMapAdapter;
import org.easyredis.adapter.RedisMapAdapter;
import org.easyredis.adapter.RedisSetMapAdapter;
import org.easyredis.util.JedisUtil;

public class AdapterFactory {
    public static IMapAdapter mapAdapter() {
        return new RedisMapAdapter(JedisUtil.jedisPool());
    }

    public static ISetMapAdapter setMapAdapter() {
        return new RedisSetMapAdapter(JedisUtil.jedisPool());
    }
}
