package com.hbfintech.repay.center.application;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(url="${tradeCenter.ms}",name="tradeCenter")
public interface TradeMsClient {
}
