package com.hbfintech.repay.center.algorithm;

import com.hbfintech.frame.common.constants.Constants;
import com.hbfintech.frame.common.utils.Utility;
import com.hbfintech.repay.center.infrastructure.annotation.ShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.util.*;

@ShardingAlgorithm
public class ProductRepayFlowRangAlgorithm implements RangeShardingAlgorithm<Date> {

    public static final String PRODUCT_REPAY_FLOW = "product_repay_flow_";

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames,
        RangeShardingValue<Date> shardingValue) {
        Set<String> result = new HashSet<>();
        Date start = Utility.parse("20211001",Constants.YYYYMMDD);
        Date end = new Date();
        if (shardingValue.getValueRange().hasLowerBound()) {
            start = shardingValue.getValueRange().lowerEndpoint();
        }

        if (shardingValue.getValueRange().hasUpperBound()) {
            end = shardingValue.getValueRange().upperEndpoint();
        }

        if (start.after(end)) {
            return availableTargetNames;
        }

        String startDateStr = Utility.formatDate(start, Constants.YYYYMM);
        String endDateStr = Utility.formatDate(end, Constants.YYYYMM);

        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(start);

        while (endDateStr.compareTo(startDateStr) >= 0) {
            result.add(PRODUCT_REPAY_FLOW + startDateStr);
            startCalendar.add(Calendar.MONTH, 1);
            startDateStr = Utility.formatDate(startCalendar.getTime(), Constants.YYYYMM);
        }
        return result;
    }
}
