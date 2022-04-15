package com.hbfintech.repay.center.algorithm;

import com.hbfintech.frame.common.constants.Constants;
import com.hbfintech.frame.common.utils.Utility;
import com.hbfintech.repay.center.infrastructure.annotation.ShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;
import java.util.Date;

@ShardingAlgorithm
public class ProductRepayFlowPreciseAlgorithm implements PreciseShardingAlgorithm<Date> {

    public static final String PRODUCT_REPAY_FLOW = "product_repay_flow_";

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Date> shardingValue) {
        String index = Utility.formatDate(shardingValue.getValue(), Constants.YYYYMM);
        return PRODUCT_REPAY_FLOW + index;
    }
}
