package org.tiger.proxy.router;

/**
 * Created by fish on 17/5/21.
 */
public class ShardingRule {


    /**
     * 判断是否为分库列
     *
     * @param shardingColumnContext
     */
    public boolean isShardingColumn(ShardingColumnContext shardingColumnContext){
        return false;
    }
}
