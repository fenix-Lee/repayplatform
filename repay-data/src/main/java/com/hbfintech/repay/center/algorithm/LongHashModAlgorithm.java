package com.hbfintech.repay.center.algorithm;

import com.hbfintech.repay.center.infrastructure.annotation.ShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

@ShardingAlgorithm
public class LongHashModAlgorithm implements PreciseShardingAlgorithm<Long> {

    public static final String PRODUCT_REPAY_RECORD = "product_repay_record_";

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> shardingValue) {
        int index = shardingValue.getValue().hashCode() % 64;
        return PRODUCT_REPAY_RECORD + Math.abs(index);
    }
}
