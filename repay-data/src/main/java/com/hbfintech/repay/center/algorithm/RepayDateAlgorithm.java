package com.hbfintech.repay.center.algorithm;

import com.google.common.collect.Range;
import com.hbfintech.frame.common.constants.Constants;
import com.hbfintech.frame.common.utils.Utility;
import com.hbfintech.repay.center.infrastructure.annotation.ShardingAlgorithm;
import org.apache.commons.collections.MapUtils;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;

import java.util.*;

@ShardingAlgorithm
public class RepayDateAlgorithm implements ComplexKeysShardingAlgorithm<Date> {

    public static final String NUGGETS_BATCH_REPAY_EXPORT_DETAIL = "product_repay_apply_record_";

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames,
                                         ComplexKeysShardingValue<Date> shardingValue) {
            Set<String> result = new HashSet<>();
            if (MapUtils.isNotEmpty(shardingValue.getColumnNameAndShardingValuesMap())) {
                Collection<Date> collection = shardingValue.getColumnNameAndShardingValuesMap().get("submeter_date");
                for (Date date : collection) {
                    //默认-10天利于批扣日
                    String index = Utility.formatDate(Utility.rollDateByDays(date, -10), Constants.YYYYMM);
                    String dataSourceName = NUGGETS_BATCH_REPAY_EXPORT_DETAIL + index;
                    result.add(dataSourceName);
                }

            }

            if (MapUtils.isNotEmpty(shardingValue.getColumnNameAndRangeValuesMap())) {
                Range range = shardingValue.getColumnNameAndRangeValuesMap().get("submeter_date");
                Date start = (Date) range.lowerEndpoint();
                Date end = (Date) range.upperEndpoint();

                //默认-10天利于批扣日
                Date actStart = Utility.rollDateByDays(start, -10);
                Date actEnd = Utility.rollDateByDays(end, -10);

                if (actStart.after(actEnd)) {
                    return availableTargetNames;
                }

                String startDateStr = Utility.formatDate(actStart, Constants.YYYYMM);
                String endDateStr = Utility.formatDate(actEnd, Constants.YYYYMM);

                Calendar startCalendar = Calendar.getInstance();
                startCalendar.setTime(actStart);

                while (endDateStr.compareTo(startDateStr) >= 0) {
                    result.add(NUGGETS_BATCH_REPAY_EXPORT_DETAIL+startDateStr);
                    startCalendar.add(Calendar.MONTH, 1);
                    startDateStr = Utility.formatDate(startCalendar.getTime(), Constants.YYYYMM);
                }


            }
            return result;
    }
}
