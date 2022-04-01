package com.hbfintech.repay.center.algorithm;

import com.hbfintech.frame.common.constants.Constants;
import com.hbfintech.frame.common.utils.Utility;
import com.hbfintech.repay.center.infrastructure.annotation.ShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;
import java.util.Date;

@ShardingAlgorithm
public class NuggetsBatchRepayAlgorithm
       implements PreciseShardingAlgorithm<Date> {

    public static final String NUGGETS_BATCH_REPAY_IMPORT_DETAIL = "nuggets_batch_repay_import_detail_";

    @Override
    public String doSharding(Collection<String> availableTargetNames,
                             PreciseShardingValue<Date> shardingValue) {
        String index = Utility.formatDate(shardingValue.getValue(), Constants.YYYYMM);
        return NUGGETS_BATCH_REPAY_IMPORT_DETAIL + index;
    }
}
